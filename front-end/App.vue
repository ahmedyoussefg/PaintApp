<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
         <li><button @click="get_element">Select</button></li>
         <li><button @click="Undo">Undo</button></li>
         <li><button @click="Redo">Redo</button></li>
         <li><button @click="Save">Save</button></li>
        <div v-if="showModalSave" class="modal">
        <div class="modal-content">
        <h2>Choose File Type</h2>
        <button >Save as XML</button>
        <button @click="saveAsJSON">Save as JSON</button>
        </div>
        </div>
         <li><button @click="Load">Load</button></li>
         <div v-if="showModalload" class="modal">
        <div class="modal-content">
        <h2>Choose File Type</h2>
        <button >Load XML File</button>
        <button @click="saveAsJSON">Load JSON File</button>
        </div>
        </div>

        <li><button @click="drawshape('pencil')"><span>&#9998;</span> Pencil</button></li>
        <li><button @click="drawshape('line')"><span style="font-size: medium;">&#9644; </span> Line</button></li>
        <li> <button @click="drawshape('circle')"> <span>&#9679; </span> Circle</button></li>
        <li><button @click="drawshape('rectangle')"><span>&#9644; </span> Rectangle</button></li>
        <li> <button @click="drawshape('square')"><span> &#9632; </span> Square</button></li>
        <li><button @click="drawshape('triangle')"><span style="font-size: smaller;">&#9650; </span>Triangle</button></li>
          <li><button class="elip" @click="drawshape('ellipse')"><svg height="20" width="30"><ellipse cx="12" cy="10" rx="10" ry="5"
              style="display: inline-block; fill:black;" /></svg><span style="font-size: large">Ellipse</span></button></li>
          <!-- <li><button @click="Editshape"><span>&#128400; </span>Edit</button></li> -->

      </ul><br>
      <div class="color-picker" @click="changeFillColor($event)">Fill Color 
    <div class="gradient"></div>
    </div><br>
    <div class="color-picker" @click="changeStrokeColor($event)">Stroke Color 
    <div class="gradient"></div>
    </div><br>
    <div class="size-picker" @click="changeStrokewidth($event)">
  <label style="font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif; font-weight: bold;" for="shapeSize">Stroke Size</label><br>
  <input type="range" id="shapeSize" min="1" max="10" v-model="shapeSize" />
</div>

    </div>
    
    
    <div class="board" ref="stageContainer">
      
    </div>
  </div>
</template>

<script>
import Konva from 'konva';
import Circle from './Circle';
import rectangle from './rectangle';
import Square from './Square';
import Triangle from './Triangle';
import ellipse   from './ellipse';
import Line from './Line';
import ShapeDTO from './ShapeDTO';
import axios from 'axios';
import { saveAs} from 'file-saver';

export default {
  data(){
    return {
      fill_color : 'aliceblue',
      stroke_color : 'black',
      stroke_Width :2,
      startx:0,
      starty:0,
      isDragging : false,
      isDrawing : false,
      shapetype :'null',
      circle: null,
      rec:null,
      sq:null,
      line : null,
      ellip : null,
      pen : null,
      triangle : null,
      shapeID:0,
      board :[],
      showModalload :false,
      showModalSave :false


    }
  },
  mounted() {
      this.stage = new Konva.Stage({
      container: this.$refs.stageContainer,
      width: window.innerWidth,
      height: window.innerHeight,
    });
    this.layer = new Konva.Layer();
    this.stage.add(this.layer);
  },

  methods: {
    Load()
    {
      this.showModalload = true
    },
    loadJSON()
    {
      // func to load JSON
      this.showModalload = false
    },

    Save() {
      this.showModalSave = true;
    },
    loadJSON(){
      
    },
     saveAsJSON() {
      this.fetchAndSaveData();
      this.showModalSave = false;
    },
    async fetchAndSaveData() {
      try {
        const response = await axios.get('http://192.168.1.114:8081/saveJSON');
        const jsonData = response.data;
        const jsonString = JSON.stringify(jsonData, null, 2);
        const blob = new Blob([jsonString], { type: 'application/json' });
        const fileName = 'data.json';
        saveAs(blob, fileName);
      } catch (error) {
        console.error('Error fetching or saving data:', error);
      }
    },
    async requestsave()
    {
      axios.post('http://192.168.1.114:8081/saveJSON')
      .then(response => {
      const jsonData = JSON.stringify(response.data, null, 2);
      const blob = new Blob([jsonData], { type: "application/json" });
      const url = URL.createObjectURL(blob);
      }) 
    },
    async requestdraw(shapeData)
    {
      axios.post('http://192.168.1.114:8081/draw',shapeData)
      .then (response => {
        this.shapeID=response.data.id;
        console.log('Shape saved successfully:',response.data)
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });
    },
    async requestundo() {
    return axios.post('http://192.168.1.114:8081/undo') // Returning the promise
    .then(response => {
      this.board = response.data;
      return this.board; 
    })
    .catch(error => {
      console.error('Error receiving shapes', error);
      throw error; 
    });
    },
    async requestredo() {
      return axios.post('http://192.168.1.114:8081/redo') // Returning the promise
    .then(response => {
      this.board = response.data;
      return this.board; 
    })
    .catch(error => {
      console.error('Error receiving shapes', error);
      throw error; 
    });
    },

  draw(boardData) {
  this.layer.removeChildren();
  for (let i = 0; i < boardData.length; i++) {

    switch (boardData[i].shapeType) {
      case 'circle': {
        this.circle = Circle.drawcircle({
          x: boardData[i].x,
          y: boardData[i].y,
          radius: boardData[i].radius,
          fill: boardData[i].fill,
          strokeWidth: boardData[i].strokeWidth,
          stroke: boardData[i].strokeColor,
          draggable: false,
        });
       
        this.layer.add(this.circle);
        this.stage.batchDraw();
        break;
      }
      case 'rectangle': {
        this.rec = rectangle.drawrectangle({
          x: boardData[i].x,
          y: boardData[i].y,
          width: boardData[i].width,
          height: boardData[i].height,
          fill: boardData[i].fill,
          strokeWidth : boardData[i].strokeWidth,
          stroke :boardData[i].strokeColor,
          draggable:false,
        });
        console.log("Test rectangle");
        console.log(this.rec);
        
        this.layer.add(this.rec); 
        this.stage.batchDraw();
        break;
      }
      case 'triangle':
      { 
        this.triangle = Triangle.drawTriangle({
        x: boardData[i].x,
        y: boardData[i].y,
        sides: boardData[i].sides,
        radius: boardData[i].radius,  
        radiusX: boardData[i].radiusX,
        side: boardData[i].side,
        fill: boardData[i].fill,
        strokeWidth : boardData[i].strokeWidth,
        stroke :boardData[i].strokeColor,
        draggable:false,
      });
      this.layer.add(this.triangle);
      this.stage.batchDraw();
        break;
      }
      case 'ellipse':{
        this.ellip = ellipse.drawellipse({
        x: boardData[i].x,
        y: boardData[i].y,
        radiusX: boardData[i].radiusX,
        radiusY: boardData[i].radiusY,
        fill: boardData[i].fill,
        strokeWidth : boardData[i].strokeWidth,
        stroke :boardData[i].strokeColor,
        draggable:false,
      });
     
      this.layer.add(this.ellip);
      this.stage.batchDraw();
        break;
      }

      case 'line_segment':{
        this.line = Line.drawLine({
        points: boardData[i].points,
        stroke: boardData[i].strokeColor,
        strokeWidth: boardData[i].strokeWidth,
        draggable:false,
      });
     
      this.layer.add(this.line);
      this.stage.batchDraw();

        break;
      }
      case 'pencil':{
      this.pen = Line.drawLine({
      stroke: boardData[i].strokeColor,
      strokeWidth: boardData[i].strokeWidth,
      lineCap: 'round', 
      lineJoin: 'round', 
      points: boardData[i].points,
      draggable:false,
    });
   
    this.layer.add(this.pen);
    this.stage.batchDraw();
      }

    }
  }
},

Undo() {
  this.requestundo()
    .then(boardData => {
      console.log(boardData);
      this.draw(boardData); // Call undo() with the updated board data
    })
    .catch(error => {
      // Handle errors if any from the requestundo() function
      console.error('Undo failed:', error);
    });
},

Redo() {
  this.requestredo()
    .then(boardData => {
      console.log(boardData);
      this.draw(boardData); // Call undo() with the updated board data
    })
    .catch(error => {
      // Handle errors if any from the requestundo() function
      console.error('Redo failed:', error);
    });
  },

    get_element(){
      this.stage.on('mouseover', (e) => {
        const shape = e.target;
        if(shape instanceof Konva.Shape){
          shape.fill('red');
          shape.shadowColor('black');
          shape.shadowBlur(10);
          shape.shadowOffset({ x: 5, y: 5 });
          shape.shadowOpacity(0.7);
          console.log('Hovered Shape ID:', this.shapeID);
          console.log(shape);
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        const shape = e.target;
        if(shape instanceof Konva.Shape){
          shape.fill('aliceblue');
          shape.shadowOpacity(0);
          this.layer.batchDraw();
        }
      });
    
  },
    drawshape(shapetype)
    {
      let Shape = new ShapeDTO();
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout')
      this.stage.off('mouseover')
      this.stage.on('mousedown', (e) => {
      if (!this.isDrawing) {
      this.isDrawing = true;
      this.startx = e.evt.offsetX;
      this.starty = e.evt.offsetY;
      if (shapetype === 'circle')
      {
        this.circle = Circle.drawcircle({
        x: this.startx,
        y: this.starty,
        radius: 0,
        fill: this.fill_color,
        strokeWidth: this.stroke_Width,
        stroke : this.stroke_color,
        draggable:false,
      });
      this.shapeID++;
      this.layer.add(this.circle);
      this.stage.batchDraw();
      
      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const radius = Math.sqrt(
            Math.pow(e.evt.offsetX - this.startx, 2) +
            Math.pow(e.evt.offsetY - this.starty, 2)
          );

          this.circle.radius(radius);
          this.stage.batchDraw();
        }
      });
    }
    else if (shapetype === 'rectangle')
    {
          this.rec = rectangle.drawrectangle({
          x: this.startx,
          y: this.starty,
          width: 0,
          height: 0,
          fill: this.fill_color,
          strokeWidth : this.stroke_Width,
          stroke :this.stroke_color,
          draggable:false,
        });
      this.shapeID++;
      this.layer.add(this.rec);
      this.stage.batchDraw();
      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const width = e.evt.offsetX - this.startx;
          const height = e.evt.offsetY - this.starty;
          this.rec.width(width);
          this.rec.height(height);
          this.stage.batchDraw();
        }

      });
      
    }
    else if (shapetype === 'square')
    {
        this.sq = Square.drawsquare({
        x: this.startx,
        y: this.starty,
        width: 0,
        height: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });
      this.shapeID++;
      this.layer.add(this.sq);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const width = e.evt.offsetX - this.startx;
          const height = e.evt.offsetY - this.starty;
          const side = Math.min(Math.abs(width), Math.abs(height));

         
          this.sq.width(side * Math.sign(width));
          this.sq.height(side * Math.sign(height));

          this.stage.batchDraw();
        }
      });
    }
    else if(shapetype==='triangle')
    {
        this.triangle = Triangle.drawTriangle({
        x: this.startx,
        y: this.starty,
        sides: 3,
        radius: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });
      this.shapeID++;
      this.layer.add(this.triangle);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const side = Math.sqrt(
            Math.pow(e.evt.offsetX - this.startx, 2) +
            Math.pow(e.evt.offsetY - this.starty, 2)
          );

          // Set the radius of the triangle to determine its size
          this.triangle.radius(side);
          this.stage.batchDraw();
        }
      });

    }

    else if (shapetype==='ellipse')
    {
      this.ellip = ellipse.drawellipse({
        x: this.startx,
        y: this.starty,
        radiusX: 0,
        radiusY: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });
      this.shapeID++;
      this.layer.add(this.ellip);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const radiusX = Math.abs(e.evt.offsetX - this.startx);
          const radiusY = Math.abs(e.evt.offsetY - this.starty);

          this.ellip.radiusX(radiusX);
          this.ellip.radiusY(radiusY);
          this.stage.batchDraw();
        }
      });

    }
    else if (shapetype==='line')
    {
        this.line = Line.drawLine({
        points: [this.startx, this.starty],
        stroke: this.stroke_color,
        strokeWidth: this.stroke_Width,
        draggable:false,
      });
      this.shapeID++;
      this.layer.add(this.line);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const endx = e.evt.offsetX;
          const endy = e.evt.offsetY;
          this.line.points([this.startx, this.starty, endx, endy]);
          this.stage.batchDraw();
        }
      });

    }
    else if (shapetype==='pencil')
    {
      let pos = this.stage.getPointerPosition();
      this.pen = Line.drawLine({
      stroke: this.stroke_color,
      strokeWidth: this.stroke_Width,
      lineCap: 'round', 
      lineJoin: 'round', 
      points: [pos.x, pos.y],
      draggable:false,
    });
    this.shapeID++;
    this.layer.add(this.pen);
    this.stage.batchDraw();
  

  this.stage.on('mousemove touchmove', () => {
    if (!this.isDrawing) {
      return;
    }
    let pos = this.stage.getPointerPosition();
    let newPoints = this.pen.points().concat([pos.x, pos.y]);
    this.pen.points(newPoints);
    this.layer.batchDraw();
  });
    }
    this.stage.on('mouseup', () => {
        if (this.isDrawing) {
          this.isDrawing = false;
          this.stage.off('mousemove');
          this.stage.off('click');
        }
        if (shapetype === 'circle')
        {
          if(this.circle.radius()==0)
            return;
          Shape.shapeType = 'circle';
          Shape.radius = this.circle.radius();
          Shape.x = this.circle.x();
          Shape.y = this.circle.y();
          Shape.fill = this.circle.fill();
          Shape.strokeColor = this.circle.stroke();
          Shape.strokeWidth = this.circle.strokeWidth();
          Shape.id = this.shapeID;
    
        }
        if (shapetype === 'rectangle')
        {
          if (this.rec.height()===0 || this.rec.width()===0)
            return;
          Shape.shapeType = 'rectangle';
          Shape.x = this.rec.x();
          Shape.y = this.rec.y();
          Shape.width = this.rec.width();
          Shape.height = this.rec.height();
          Shape.fill = this.rec.fill();
          Shape.strokeColor = this.rec.stroke();
          Shape.strokeWidth = this.rec.strokeWidth();
          Shape.id = this.shapeID;


        }
        if (shapetype === 'square')
        {
          if (this.sq.height()===0 || this.sq.width()===0)
            return;
          Shape.shapeType = 'rectangle';
          Shape.x = this.sq.x();
          Shape.y = this.sq.y();
          Shape.width = this.sq.width();
          Shape.height = this.sq.height();
          Shape.fill = this.sq.fill();
          Shape.strokeColor = this.sq.stroke();
          Shape.strokeWidth = this.sq.strokeWidth();
          Shape.id = this.shapeID;
        }
        if (shapetype === 'triangle')
        {
          if (this.triangle.radius()==0)
            return;
          Shape.shapeType = 'triangle';
          Shape.x = this.triangle.x();
          Shape.y = this.triangle.y();
          Shape.radius = this.triangle.radius();
          Shape.sides = this.triangle.sides()
          Shape.fill = this.triangle.fill();
          Shape.strokeColor = this.triangle.stroke();
          Shape.strokeWidth = this.triangle.strokeWidth();
          Shape.id = this.shapeID;
        }
        if (shapetype === 'ellipse')
        {
          if(this.ellip.radiusX()==0 || this.ellip.radiusY()==0)
            return;
          Shape.shapeType = 'ellipse';
          Shape.radiusX = this.ellip.radiusX();
          Shape.radiusY = this.ellip.radiusY();
          Shape.x = this.ellip.x();
          Shape.y = this.ellip.y();
          Shape.fill = this.ellip.fill();
          Shape.strokeColor = this.ellip.stroke();
          Shape.strokeWidth = this.ellip.strokeWidth();
          Shape.id = this.shapeID;
    
        }
        if (shapetype === 'line')
        {
          if(this.line.points().length<4)
            return;
          Shape.shapeType = 'line_segment';
          Shape.points = this.line.points();
          Shape.fill = this.line.fill();
          Shape.strokeColor = this.line.stroke();
          Shape.strokeWidth = this.line.strokeWidth();
          Shape.id = this.shapeID;
    
        }
        if (shapetype === 'pencil')
        {
          if(this.pen.points().length<=2)
            return;
          Shape.shapeType = 'pencil';
          Shape.points = this.pen.points();
          Shape.fill = this.pen.fill();
          Shape.strokeColor = this.pen.stroke();
          Shape.strokeWidth = this.pen.strokeWidth();
          Shape.id = this.shapeID;
    
        }
        


        this.requestdraw(Shape);
        this.stage.off('mouseup');

      });
    }
      });
    },

    // Editshape(){
    // this.stage.off('mousedown');
    // this.isDragging = true;

    // this.layer.getChildren(node => {
    //   node.setAttr('draggable', this.isDragging);
    // });

    // if (!this.isDragging) {
    //   this.layer.off('mousedown touchstart');
    //   this.layer.off('mousemove touchmove');
    //   this.layer.off('mouseup touchend');
    // }

   

    // },
  

changeFillColor(event) {
    const gradient = document.querySelector('.gradient');
    const rect = gradient.getBoundingClientRect();
    const x = event.clientX - rect.left; 

    const colorStops = [
      '#000000', '#333333', '#666666', '#999999', '#CCCCCC', '#DDDDDD', '#EEEEEE', '#FFFFFF',
      '#FF0000', '#FF9900', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9900FF', '#FF00FF',
      '#FF99CC', '#FFCC99', '#FFFF99', '#CCFFCC', '#99FFFF', '#CCFFFF', '#9999FF', '#FF99FF',
      '#FFCCCC', '#FFCC99', '#FFFFCC', '#CCFFCC', '#99FFCC', '#CCFFFF', '#99CCFF', '#FF99FF'
    ];
    const percentage = (x / rect.width) * 100;
    const colorIndex = Math.floor((percentage / 100) * (colorStops.length - 1));
      this.fill_color = colorStops[colorIndex];
 

  },
  changeStrokeColor(event) {
    const gradient = document.querySelector('.gradient');
    const rect = gradient.getBoundingClientRect();
    const x = event.clientX - rect.left; 

    const colorStops = [
      '#000000', '#333333', '#666666', '#999999', '#CCCCCC', '#DDDDDD', '#EEEEEE', '#FFFFFF',
      '#FF0000', '#FF9900', '#FFFF00', '#00FF00', '#00FFFF', '#0000FF', '#9900FF', '#FF00FF',
      '#FF99CC', '#FFCC99', '#FFFF99', '#CCFFCC', '#99FFFF', '#CCFFFF', '#9999FF', '#FF99FF',
      '#FFCCCC', '#FFCC99', '#FFFFCC', '#CCFFCC', '#99FFCC', '#CCFFFF', '#99CCFF', '#FF99FF'
    ];
    const percentage = (x / rect.width) * 100;
    const colorIndex = Math.floor((percentage / 100) * (colorStops.length - 1));
      this.stroke_color = colorStops[colorIndex];
 

  },
  changeStrokewidth(event){
    this.stroke_Width = parseInt(event.target.value);
  }
  },
  

  
};
</script>

<style>
  body
  {
    background-color: rgb(230, 88, 164);
    margin: 0;
  }
  .main
  {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    height: 100vh;


  }
  .tools
  {
    width: 20%;
    background-color: aliceblue;
    margin: 50px 50px;
    border-radius: 10px;
    
  }
  .board
  {
    margin-top: 50px;
    margin-bottom: 50px;
    margin-right: 50px;
    width: 70%;
    background-color: aliceblue;
    border-radius: 10px;
    overflow: scroll;
  }
  .shapes
  {
    margin: 0px 0px;
    padding: 0px 0px;  
  }
  li
  {
    list-style: none;
  
  }
.modal {
  display: block;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.4);
}

.modal-content{
  background-color: #fefefe;
  margin: 15% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 40%;
  text-align: center;
}
  button
  {
    box-shadow: none;
    outline: none;
    padding: 10px;
    background: none;
    border: none;
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-size: larger;

  }
  li button:hover
  {
    color: #e658a4;
  }
  span
  {
    font-size: larger;    
  }
  .elip {
    display: flex;
  }
  svg :hover
  {
    fill: rgb(230, 88, 164);
  }
  .color-picker {
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
    font-weight: bold;
  width: 100%;
  position: relative;
  overflow: hidden;
  cursor: pointer;
}

.gradient {
  height: 20px;
  width: 100%;
  background: linear-gradient(to right, 
    #000000, #333333, #666666, #999999, #CCCCCC, #DDDDDD, #EEEEEE, #FFFFFF, 
    #FF0000, #FF9900, #FFFF00, #00FF00, #00FFFF, #0000FF, #9900FF, #FF00FF, 
    #FF99CC, #FFCC99, #FFFF99, #CCFFCC, #99FFFF, #CCFFFF, #9999FF, #FF99FF, 
    #FFCCCC, #FFCC99, #FFFFCC, #CCFFCC, #99FFCC, #CCFFFF, #99CCFF, #FF99FF
  );
}
.size-picker input{
  width: 95%;
  
} 
</style>