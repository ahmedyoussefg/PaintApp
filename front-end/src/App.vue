<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
        <li><button @click="hover_element"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'arrow-pointer']" /></span> Select</button></li>
        <li><button @click="Undo"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'arrow-rotate-left']" /></span> Undo</button></li>
        <li><button @click="Redo"><span style="font-size:smaller;"><font-awesome-icon :icon="['fas', 'arrow-rotate-right']" /></span> Redo</button></li>
        <li><button @click="Save"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'floppy-disk']" /></span> Save</button></li>
        <div v-if="showModalSave" class="modal">
          <div class="modal-content">
            <h2>Choose File Type</h2>
            <button >Save as XML</button>
            <button @click="saveAsJSON">Save as JSON</button>
          </div>
        </div>
        <li><button @click="Load"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'cloud-arrow-up']" /></span> Load</button></li>
        <div v-if="showModalload" class="modal">
          <div class="modal-content">
            <h2>Choose File Type</h2>
            <button >Load XML File</button>
            <button @click="saveAsJSON">Load JSON File</button>
          </div>
        </div>

        <li><button @click="drawshape('pencil')"><span>&#9998;</span> Pencil</button></li>
        <li><button @click="drawshape('line')"><span style="font-size: medium;">&#9644; </span> Line</button></li>
        <li><button @click="drawshape('circle')"> <span> &#9679;</span> Circle</button></li>
        <li><button @click="drawshape('rectangle')"><span>&#9646;</span> Rectangle</button></li>
        <li><button @click="drawshape('square')"><span> &#9632; </span> Square</button></li>
        <li><button @click="drawshape('triangle')"><span style="font-size: smaller;">&#9650; </span>Triangle</button></li>
        <li><button class="elip" @click="drawshape('ellipse')"><span style="font-size: smaller;">&#11052; </span> Ellipse</button></li>
        <li><button @click="erase"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'eraser']" /></span> Eraser</button></li>
        <li><button class = "clear" @click="clear"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'broom']" /></span> Clear Canvas</button></li>
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
    <div class="board" ref="stageContainer"></div>
    <div id="popup" class="overlay" style="position: absolute; display: block; top: 250px; right: 70px;">
      <ul>
        <li><button @click="copy_shape"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'copy']" /></span> Copy</button></li>
        <li><button @click="delete_shape"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'trash-can']" /></span> Delete</button></li>
      </ul>
    </div>
  </div>
</template>

<script>
import Konva from 'konva';
import Circle from '../src/Circle';
import rectangle from '../src/rectangle';
import Square from '../src/Square';
import Triangle from '../src/Triangle';
import ellipse   from '../src/ellipse';
import Line from '../src/Line';
import ShapeDTO from '../src/ShapeDTO';
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
      shapeID: 1,
      board :[],
      showModalload :false,
      showModalSave :false,
      shape: null,
      transformer: null,
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
        const response = await axios.get('http://192.168.0.150:8081/saveJSON');
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
      axios.post('http://192.168.0.150:8081/saveJSON')
      .then(response => {
      const jsonData = JSON.stringify(response.data, null, 2);
      const blob = new Blob([jsonData], { type: "application/json" });
      const url = URL.createObjectURL(blob);
      }) 
    },
    async requestdraw(shapeData)
    {
      axios.post('http://192.168.0.150:8081/draw',shapeData)
      .then (response => {
        console.log('Shape saved successfully:',response.data.id);
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });
    },
    async requestundo() {
    return axios.post('http://192.168.0.150:8081/undo') // Returning the promise
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
      return axios.post('http://192.168.0.150:8081/redo') // Returning the promise
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
    this.draw_instance(boardData[i]);
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

  draw_instance(shape){
    switch (shape.shapeType) {
      case 'circle': {
        this.circle = Circle.drawcircle({
          x: shape.x,
          y: shape.y,
          radius: shape.radius,
          fill: shape.fill,
          strokeWidth: shape.strokeWidth,
          stroke: shape.strokeColor,
          draggable: false,
          id: shape.id.toString()
        });
       
        this.layer.add(this.circle);
        this.stage.batchDraw();
        break;
      }
      case 'rectangle': {
        this.rec = rectangle.drawrectangle({
          x: shape.x,
          y: shape.y,
          width: shape.width,
          height: shape.height,
          fill: shape.fill,
          strokeWidth : shape.strokeWidth,
          stroke :shape.strokeColor,
          draggable:false,
          id: shape.id.toString()
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
        x: shape.x,
        y: shape.y,
        sides: shape.sides,
        radius: shape.radius,  
        radiusX: shape.radiusX,
        side: shape.side,
        fill: shape.fill,
        strokeWidth : shape.strokeWidth,
        stroke :shape.strokeColor,
        draggable:false,
        id: shape.id.toString()
      });
      this.layer.add(this.triangle);
      this.stage.batchDraw();
        break;
      }
      case 'ellipse':{
        this.ellip = ellipse.drawellipse({
        x: shape.x,
        y: shape.y,
        radiusX: shape.radiusX,
        radiusY: shape.radiusY,
        fill: shape.fill,
        strokeWidth : shape.strokeWidth,
        stroke :shape.strokeColor,
        draggable:false,
        id: shape.id.toString()
      });
     
      this.layer.add(this.ellip);
      this.stage.batchDraw();
        break;
      }

      case 'line_segment':{
        this.line = Line.drawLine({
        points: shape.points,
        stroke: shape.strokeColor,
        strokeWidth: shape.strokeWidth,
        draggable:false,
        id: shape.id.toString()
      });
     
      this.layer.add(this.line);
      this.stage.batchDraw();

        break;
      }
      case 'pencil':{
      this.pen = Line.drawLine({
      stroke: shape.strokeColor,
      strokeWidth: shape.strokeWidth,
      lineCap: 'round', 
      lineJoin: 'round', 
      points: shape.points,
      draggable:false,
      id: shape.id.toString()
    });
   
    this.layer.add(this.pen);
    this.stage.batchDraw();
      }
    }
  },


  hover_element(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout'); 
      this.stage.off('click');


      let original_color;
      let original_stroke;
      
      this.stage.on('click', (e) => {
        const clickedShape = e.target;
        if(clickedShape !== this.stage){
          if (this.transformer) {
              if(clickedShape instanceof Konva.Shape){
                this.transformer.nodes([clickedShape]);
                //this.transformer.moveToTop();
              }else{
                this.transformer.destroy();
              }
          }

          if (clickedShape instanceof Konva.Shape) {
              if (!this.transformer) {
                  this.transformer = new Konva.Transformer({
                      nodes: [clickedShape],
                      centeredScaling: true,
                  });
                  this.layer.add(this.transformer);
              }
          }
        }else{
          if(this.transformer){
            this.transformer.nodes([]);
          }
        }
      });

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Change Color
          original_color = this.shape.fill();
          original_stroke = this.shape.getStroke();

          console.log(this.shape.id());
          if(original_color !== '#FF0C59'){
            this.shape.fill('#FF0C59');
          }
          else{
            this.shape.fill('red');
          }

          if(original_stroke !== '#43FFF9'){
            this.shape.setStroke('#43FFF9');
          }
          else{
            this.shape.setStroke('#49FF43')
          }
          // Add Shadow
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          document.body.style.cursor = 'move';
          this.shape.draggable(true);

          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        this.shape = e.target;

        if(this.shape instanceof Konva.Shape){
          document.body.style.cursor = 'default';
          this.shape.draggable(false);
          //Return Color
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          //Remove Shadow
          this.shape.shadowOpacity(0);
          this.layer.draw();
        }
      });
    },

    clear(){
      this.stage.destroyChildren();
      this.stage.draw();
      
      this.stage = new Konva.Stage({
        container: this.$refs.stageContainer,
        width: window.innerWidth,
        height: window.innerHeight,
      });
      this.layer = new Konva.Layer();
      this.stage.add(this.layer);
    },

    ////////////////////////////////
    erase(){
      this.fill_color = "aliceblue";
      this.usePencilTool();
    },

    //////////////////////////////
    fill_shape(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      
      let original_color;

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Change Color
          document.body.style.cursor = 'not-allowed';
          original_color = this.shape.fill();
          if(original_color !== '#FF0C59'){
            this.shape.fill('#FF0C59');
          }
          else{
            this.shape.fill('white');
          }
          // Add Shadow
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          //Fill shape on Click
          this.shape.on('click', ()=>{
            this.shape.destroy(); 
            document.body.style.cursor = 'default';
          })
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape){
          this.shape.fill(original_color);
        }
      })
    },
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    async requestcopy(shapeid)
    {
      axios.post('http://192.168.0.150:8081/copy',[shapeid, this.shapeID.toString()])
      .then (response => {
        this.shapeID++;
        console.log('Shape copied successfully:',response.data),
        console.log('shapeID', this.shapeID.toString());
        console.log('Id of copied shape', shapeid);
        this.draw_instance(response.data);
        return response.data;
      })
      .catch (error => {
        console.error('Error copying shape:' , error);
      });
    },

    copy_shape(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      this.stage.off('click');

      let copiedShape = null;
      let original_color;
      let original_stroke;

      
      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Change Color
          document.body.style.cursor = 'grab';
          original_color = this.shape.fill();
          original_stroke = this.shape.getStroke();

          if(original_color !== '#FF0C59'){
            this.shape.fill('#FF0C59');
          }
          else{
            this.shape.fill('red');
          }

          if(original_stroke !== '#43FFF9'){
            this.shape.setStroke('#43FFF9');
          }
          else{
            this.shape.getStroke('#49FF43')
          }

          // Add Shadow
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          this.layer.batchDraw();
          if(copiedShape !== null)
            this.draw_instance(copiedShape);
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          this.shape.shadowOpacity(0)
        }
      });

      //Copy Shape On click
      if(this.shape){
        this.shape.on('click', ()=>{
          //if(this.transformer){
            //console.log("IF");
            //console.log("Shape ID:  SSS ", this.shape.id());
            //copiedShape = this.requestcopy(this.shape.id());
            //this.transformer.destroy();
            //this.transformer = null;
            //document.body.style.cursor = 'default';
          //}
          //else{
            console.log("Shape", this.shape.id());
            //console.log("ELSE");
            copiedShape = this.requestcopy(this.shape.id());
            document.body.style.cursor = 'default';
          //}
        });
      }
    },

    async requestdelete(shapeid){
      axios.post('http://192.168.0.150:8081/delete',shapeid)
      .then (response => {
        console.log(shapeid);
        console.log('Shape deleted successfully:',response.data),
        console.log('shapeID', this.shapeID.toString());
        console.log('Id of Deleted shape', shapeid);
      })
      .catch (error => {
        console.error('Error deleting shape:' , error);
      });
    },

    delete_shape(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      this.stage.off('click');

      let original_color;
      let original_stroke;

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Change Color
          document.body.style.cursor = 'not-allowed';
          original_color = this.shape.fill();
          original_stroke = this.shape.getStroke();

          if(original_color !== '#FF0C59'){
            this.shape.fill('#FF0C59');
          }
          else{
            this.shape.fill('red');
          }

          if(original_stroke !== '#43FFF9'){
            this.shape.setStroke('#43FFF9');
          }
          else{
            this.shape.getStroke('#49FF43')
          }

          // Add Shadow
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
        }
      });

      //Delete Shape On click
      if(this.shapeID > 1){
        this.shape.on('click', ()=>{
          if(this.transformer){
            console.log("IF Deleting");
            console.log(this.shape.id);
            this.requestdelete(this.shape.id);
            this.shape.destroy();
            this.transformer.destroy();
            this.transformer = null;
            document.body.style.cursor = 'default';
          }
          else{
            console.log("Else Deleting");
            this.requestdelete(this.shape.id);
            this.shape.destroy();
            document.body.style.cursor = 'default';
          }
        });
      }
    },

    drawshape(shapetype)
    {
      let Shape = new ShapeDTO();
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      this.stage.off('mouseover');
      this.stage.off('click');

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
        id: this.shapeID.toString()
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
          id: this.shapeID.toString()
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
        this.sq = Square.drawsquare({
        x: this.startx,
        y: this.starty,
        width: 0,
        height: 0,
        fill: this.fill_color,
        strokeWidth : this.stroke_Width,
        stroke :this.stroke_color,
        draggable:false,
        id: this.shapeID.toString()
      });
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
        id: this.shapeID.toString()
      });
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
        id: this.shapeID.toString()
      });
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
        id: this.shapeID.toString()
      });
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
      id: this.shapeID.toString()
    });
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
        this.shapeID++;
      });
    }
      });
    },
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