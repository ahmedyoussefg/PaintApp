<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
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
import ShapeFactory from './ShapeFactory';

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
      rec:null
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

    requestBackend(shapeData)
    {
      axios.post('http://localhost:8081/draw',shapeData)
      .then (response => {
        console.log('Shape saved successfully:',response.data)
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });

      
    },
    drawshape(shapetype)
    {
      let Shape = new ShapeDTO();
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
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
      let sq;
        sq = Square.drawrsquare({
        x: this.startx,
        y: this.starty,
        width: 0,
        height: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });
      this.layer.add(sq);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const width = e.evt.offsetX - this.startx;
          const height = e.evt.offsetY - this.starty;
          const side = Math.min(Math.abs(width), Math.abs(height));

         
          sq.width(side * Math.sign(width));
          sq.height(side * Math.sign(height));

          this.stage.batchDraw();
        }
      });
    }
    else if(shapetype==='triangle')
    {
      let triangle;
      triangle = Triangle.drawTriangle({
        x: this.startx,
        y: this.starty,
        sides: 3,
        radius: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });

      this.layer.add(triangle);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const side = Math.sqrt(
            Math.pow(e.evt.offsetX - this.startx, 2) +
            Math.pow(e.evt.offsetY - this.starty, 2)
          );

          // Set the radius of the triangle to determine its size
          triangle.radius(side);
          this.stage.batchDraw();
        }
      });

    }

    else if (shapetype==='ellipse')
    {
      let ellip;
      ellip = ellipse.drawellipse({
        x: this.startx,
        y: this.starty,
        radiusX: 0,
        radiusY: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
      });

      this.layer.add(ellip);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const radiusX = Math.abs(e.evt.offsetX - this.startx);
          const radiusY = Math.abs(e.evt.offsetY - this.starty);

          ellip.radiusX(radiusX);
          ellip.radiusY(radiusY);
          this.stage.batchDraw();
        }
      });

    }
    else if (shapetype==='line')
    {
      let line;
      line = Line.drawLine({
        points: [this.startx, this.starty],
        stroke: this.stroke_color,
        strokeWidth: this.stroke_Width,
        draggable:false,
      });

      this.layer.add(line);
      this.stage.batchDraw();

      this.stage.on('mousemove', (e) => {
        if (this.isDrawing) {
          const endx = e.evt.offsetX;
          const endy = e.evt.offsetY;
          line.points([this.startx, this.starty, endx, endy]);
          this.stage.batchDraw();
        }
      });

    }
    else if (shapetype==='pencil')
    {
      let pos = this.stage.getPointerPosition();
      let lastLine = Line.drawLine({
      stroke: this.stroke_color,
      strokeWidth: this.stroke_Width,
      lineCap: 'round', 
      lineJoin: 'round', 
      points: [pos.x, pos.y],
      draggable:false,
    });
    this.layer.add(lastLine);
  

  this.stage.on('mousemove touchmove', () => {
    if (!this.isDrawing) {
      return;
    }
    let pos = this.stage.getPointerPosition();
    let newPoints = lastLine.points().concat([pos.x, pos.y]);
    lastLine.points(newPoints);
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
          Shape.shapeType = 'circle';
          Shape.radius = this.circle.radius();
          Shape.x = this.circle.x();
          Shape.y = this.circle.y();
          Shape.fill = this.circle.fill();
          Shape.strokeColor = this.circle.stroke();
          Shape.strokeWidth = this.circle.strokeWidth();

        }
        if (shapetype === 'rectangle')
        {
          Shape.shapeType = 'rectangle';
          Shape.x = this.rec.x();
          Shape.y = this.rec.y();
          Shape.fill = this.rec.fill();
          Shape.strokeColor = this.rec.stroke();
          Shape.strokeWidth = this.rec.strokeWidth();

        }
        console.log(Shape);
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