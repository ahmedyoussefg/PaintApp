<template>
    <div class="main">
      <div class="tools">
        <div class="shapes">
          <button @click="hover_element(); reset_selection()"><span style="font-size: x-large"><font-awesome-icon :icon="['fas', 'arrow-pointer']" /></span> </button>         
         
           <div v-if="showModalSave" class="modal">
            <div class="modal-content">
              <h2>Choose File Type</h2>
              <button @click="saveAsXML">Save as XML</button>
              <button @click="saveAsJSON">Save as JSON</button>
            </div>
          </div>
         
          <div v-if="showModalload" class="modal">
            <div class="modal-content">
              <h2>Choose File Type</h2>
              <button @click="loadXML">Load XML File</button>
              <button @click="loadJSON">Load JSON File</button>
            </div>
          </div> 
          
          <div class="A">
          <button v-if="!clickedcircle"  @click="drawshape('circle'); reset_selection();" title="Circle"> <span style="font-size: x-large; font-weight: bold;"> &#9711;</span> </button>
          <button style="color: aliceblue;" v-if="clickedcircle"  @click="drawshape('circle'); reset_selection();" title="Circle"> <span style="font-size: x-large; font-weight: bold;"> &#9711;</span> </button>
          <button v-if="!clickedrec" @click="drawshape('rectangle'); reset_selection();" title="Rectangle"><span style="font-size: xx-large;">&#9645;</span></button>
          <button v-if="clickedrec"  style="color: aliceblue;" @click="drawshape('rectangle'); reset_selection();" title="Rectangle"><span style="font-size: xx-large;">&#9645;</span></button>
          <button v-if="!clickedsq" @click="drawshape('square'); reset_selection();" title="Square"><span style="font-size: xx-large;">&#9723; </span></button>
          <button v-if="clickedsq"  style="color: aliceblue;" @click="drawshape('square'); reset_selection();" title="Square"><span style="font-size: xx-large;">&#9723; </span></button>
          <button v-if="!clickedtr" @click="drawshape('triangle'); reset_selection();" title="Triangle"><span style="font-size: xx-large;">&#9651;</span></button>
          <button v-if="clickedtr"  style="color: aliceblue;" @click="drawshape('triangle'); reset_selection();" title="Triangle"><span style="font-size: xx-large;">&#9651;</span></button>
          <button v-if="!clickedellipse" class="elip" @click="drawshape('ellipse'); reset_selection();" title="Ellipse"><span style="font-size: xx-large;">&#11053;</span> </button>
          <button v-if="clickedellipse" style="color: aliceblue;" class="elip" @click="drawshape('ellipse'); reset_selection();" title="Ellipse"><span style="font-size: xx-large;">&#11053;</span> </button>
          <button v-if="!clickedline" @click="drawshape('line'); reset_selection();" title="Line"><span style="font-size: xx-large;">/</span></button>
          <button v-if="clickedline"  style="color: aliceblue;" @click="drawshape('line'); reset_selection();" title="Line"><span style="font-size: xx-large;">/</span></button>
          <button v-if="!clickedpen" @click="drawshape('pencil'); reset_selection();" title="Pencil"><span style="font-size: xx-large;">&#9998;</span> </button>
          <button v-if="clickedpen"  style="color: aliceblue;" @click="drawshape('pencil'); reset_selection();" title="Pencil"><span style="font-size: xx-large;">&#9998;</span> </button>

          </div>

          <div style="margin-left: 10px;" class="A">
            <button title="eraser" @click="erase(); reset_selection()"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'eraser']" /></span></button>
            <button title="clear" class = "clear" @click="clear(); reset_selection()"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'broom']" /></span> </button>


          </div>

          <div class="B">
            <!-- <label for="colorfill">Fill Color</label> -->
            <button v-if="!clickedpen" title="fill" @click="fill_shape(); ; reset_selection();"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'fill']" /></span></button>
            <input class="color" title="Fill" v-model="fill_color"  type="color" id="colorfill">
            <input class="color" title="Stroke" v-model="stroke_color"  type="color" id="colorstroke">
            <select name="strokewidth" v-model="stroke_Width" id="widthstroke">
              <option value="2">Size</option>
              <option value="4">4</option>
              <option value="6">6</option>
              <option value="8">8</option>
              <option value="10">10</option>
              <option value="12">12</option>
              <option value="14">14</option>
              <option value="16">16</option>
              <option value="18">18</option>
              <option value="20">20</option>
            </select>
          </div>

          <div style="margin-left: 30px;" class="A">
            <button title="Undo" @click="Undo"><span style="font-size: xx-large;">&#8617;</span> </button>
            <button title="Redo" @click="Redo"><span style="font-size:xx-large;">&#8618;</span> </button>
          </div>

          <div class="A">
            <button @click="Save(); reset_selection()"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'floppy-disk']" /></span> Save</button>
            <button @click="Load(); reset_selection()"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'cloud-arrow-up']" /></span> Load</button>
          </div>



          
        </div><br>
          
      </div>
      <div class="board" ref="stageContainer"></div>
      <!-- <div id="popup" class="overlay" style="position: absolute; display: block; top: 250px; right: 70px;">
        <ul>
          <li><button @click="copy_shape"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'copy']" /></span> Copy</button></li>
          <li><button @click="delete_shape"><span style="font-size: smaller;"><font-awesome-icon :icon="['fas', 'trash-can']" /></span> Delete</button></li>
        </ul>
      </div> -->
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
        const response = await axios.get('http://192.168.0.192:8081/saveJSON');
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
      axios.post('http://192.168.0.192/saveJSON')
      .then(response => {
      const jsonData = JSON.stringify(response.data, null, 2);
      const blob = new Blob([jsonData], { type: "application/json" });
      const url = URL.createObjectURL(blob);
      }) 
    },
    async requestdraw(shapeData)
    {
      axios.post('http://192.168.0.192:8081/draw',shapeData)
      .then (response => {
        console.log('Shape saved successfully:',response.data.id);
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });
    },
    async requestundo() {
    return axios.post('http://192.168.0.192:8081/undo') // Returning the promise
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
      return axios.post('http://192.168.0.192:8081/redo') // Returning the promise
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
async requestClear(){
  return axios.get('http://192.168.0.192:8081/clear') // Returning the promise
    .then(response => {
        console.log('cleared the canvas');
    })
    .catch(error => {
      console.log('error clearing the canvas');
      throw error;
    });
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
          rotation: shape.rotation,
          scaleX: shape.scaleX,
          
          scaleY: shape.scaleY,
          offsetX: shape.offsetX,
        offsetY: shape.offsetY,
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
          rotation: shape.rotation,
          scaleX: shape.scaleX,
          
          scaleY: shape.scaleY,
          offsetX: shape.offsetX,
        offsetY: shape.offsetY,
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
        fill: shape.fill,
        strokeWidth : shape.strokeWidth,
        stroke :shape.strokeColor,
        rotation: shape.rotation,
          scaleX: shape.scaleX,
          scaleY: shape.scaleY,
          offsetX: shape.offsetX,
        offsetY: shape.offsetY,
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
        rotation: shape.rotation,
          scaleX: shape.scaleX,
          scaleY: shape.scaleY,
          offsetX: shape.offsetX,
        offsetY: shape.offsetY,
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
        x: shape.x,
        y: shape.y,
        stroke: shape.strokeColor,
        strokeWidth: shape.strokeWidth,
        rotation: shape.rotation,
        scaleX: shape.scaleX,
        scaleY: shape.scaleY,
        draggable:false,
        offsetX: shape.offsetX,
        offsetY: shape.offsetY,
        id: shape.id.toString()
      });
     
      this.layer.add(this.line);
      this.stage.batchDraw();

        break;
      }
      case 'pencil':{
      this.pen = Line.drawLine({
      x: shape.x,
      y: shape.y,
      stroke: shape.strokeColor,
      strokeWidth: shape.strokeWidth,
      lineCap: 'round', 
      lineJoin: 'round', 
      points: shape.points,
      draggable:false,
      rotation: shape.rotation,
      scaleX: shape.scaleX,
      scaleY: shape.scaleY,
      offsetX: shape.offsetX,
      offsetY: shape.offsetY,
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
        console.log(e);
        if(clickedShape !== this.stage && !(clickedShape instanceof Konva.Line && clickedShape.strokeColor==="aliceblue")){
          this.shape = clickedShape;
          if (this.transformer){
              if(clickedShape instanceof Konva.Shape){
                this.transformer.nodes([clickedShape]);
                console.log("Hello from existing transformer");
              }
          }else{
            e.target.on('transformend', ()=>{
                console.log('transform ended');
                let sent_shape = e.target;
                if(sent_shape.fill() === '#FF0C59'){
                  sent_shape.fill(original_color);
                }
                
                if(sent_shape.getStroke() === '#43FFF9'){
                  sent_shape.setStroke(original_stroke);
                }
                this.requestUpdate(sent_shape);
              });
            if(clickedShape instanceof Konva.Shape){
              this.transformer = new Konva.Transformer({
                  nodes: [clickedShape],
                  centeredScaling: true,
                  ignoreStroke: true,
              });
              this.transformer.moveToTop();
              console.log("Hello from creating transformer");
              this.layer.add(this.transformer);
            }
          }
        }else{
          if(this.transformer){
            console.log("Hiding Transformer from stage");
            this.transformer.destroy();
            this.transformer = new Konva.Transformer({
                  nodes: [],
                  centeredScaling: true,
                  ignoreStroke: true,
            });
            this.transformer.moveToTop();
            this.layer.add(this.transformer);
          }
        }
      });

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          // Change Color
          original_color = this.shape.fill();
          original_stroke = this.shape.getStroke();

          if(this.shape !== this.transformer){
              console.log(this.shape.id());
          }
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

        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          document.body.style.cursor = 'default';
          this.shape.draggable(false);
          //Return Color
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          //Remove Shadow
          this.shape.shadowOpacity(0);
          this.layer.draw();
          console.log(this.shape);
        }
      });
      this.stage.off('dragend').on('dragend', (e)=>{
        if (e.target instanceof Konva.Shape) {
          console.log('request update');
          let sent_shape = e.target;
          sent_shape.draggable(false);
          //Return Color
          sent_shape.fill(original_color);
          sent_shape.setStroke(original_stroke);
          //Remove Shadow
          sent_shape.shadowOpacity(0);
          this.requestUpdate(sent_shape);
        }
      });
    },
    convertShapetoShapeDTO(shape){
      let dto=new ShapeDTO();
      
      if (shape.className === 'Circle')
        {
          dto.shapeType = 'circle';
          dto.radius = shape.radius();
          dto.x = shape.x();
          dto.y = shape.y();
          dto.fill = shape.fill();
          dto.strokeColor = shape.stroke();
          dto.strokeWidth = shape.strokeWidth();
          dto.id = shape.getId();
          dto.scaleX = shape.scaleX();
          dto.scaleY = shape.scaleY();
          dto.rotation = shape.rotation();
          dto.offsetX=shape.offsetX();
          dto.offsetY=shape.offsetY();
        }
        if (shape.className === 'Rect')
        {
          dto.shapeType = 'rectangle';
          dto.width = shape.width();
          dto.height = shape.height();
          dto.x = shape.x();
          dto.y = shape.y();
          dto.fill = shape.fill();
          dto.strokeColor = shape.stroke();
          dto.strokeWidth = shape.strokeWidth();
          dto.id = shape.getId();
          dto.scaleX = shape.scaleX();
          dto.scaleY = shape.scaleY();
          dto.rotation = shape.rotation();
          dto.offsetX=shape.offsetX();
          dto.offsetY=shape.offsetY();
        }
        if (shape.className=== 'RegularPolygon') // triangle
        {
          dto.shapeType = 'triangle';
          dto.x = shape.x();
          dto.y = shape.y();
          dto.radius = shape.radius();
          dto.sides = shape.sides()
          dto.fill = shape.fill();
          dto.strokeColor = shape.stroke();
          dto.strokeWidth = shape.strokeWidth();
          dto.id = shape.getId();
          dto.scaleX = shape.scaleX();
          dto.scaleY = shape.scaleY();
          dto.rotation = shape.rotation();
          dto.offsetX=shape.offsetX();
          dto.offsetY=shape.offsetY();
        }
        if (shape.className === 'Ellipse')
        {
          dto.shapeType = 'ellipse';
          dto.radiusX = shape.radiusX();
          dto.radiusY = shape.radiusY();
          dto.x = shape.x();
          dto.y = shape.y();
          dto.fill = shape.fill();
          dto.strokeColor = shape.stroke();
          dto.strokeWidth = shape.strokeWidth();
          dto.id = shape.getId();
          dto.scaleX = shape.scaleX();
          dto.scaleY = shape.scaleY();
          dto.rotation = shape.rotation();
          dto.offsetX=shape.offsetX();
          dto.offsetY=shape.offsetY();
        }
        if (shape.className === 'Line')
        {
          dto.shapeType = 'line_segment';
          dto.points = shape.points();
          dto.fill = shape.fill();
          dto.strokeColor = shape.stroke();
          dto.strokeWidth = shape.strokeWidth();
          dto.id = shape.getId();   
          dto.scaleX = shape.scaleX();
          dto.scaleY = shape.scaleY();
          dto.rotation = shape.rotation(); 
          dto.offsetX=shape.offsetX();
          dto.offsetY=shape.offsetY();
        }
        return dto;
    },
    async requestUpdate(modifiedShape){
      console.log(modifiedShape);
      let dto_sent = this.convertShapetoShapeDTO(modifiedShape);
      console.log(dto_sent);
        return axios.post('http://192.168.0.192:8081/update', dto_sent) // Returning the promise
      .then(response => {

      })
      .catch(error => {
        console.error('Error receiving shapes', error);
        throw error; 
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
      this.requestClear();
    },

    //////////////////////////////
    fill_shape(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      this.stage.off('click');

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Add Shadow
          document.body.style.cursor = 'pointer';
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          //Fill shape on Click
          this.shape.on('click', ()=>{
            this.shape.fill(this.fill_color);
            this.requestUpdate(this.shape)
          });
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape){
          this.shape.shadowOpacity(0);
        }
      });
    },

    reset_selection(){
      if(this.transformer){
        this.transformer.destroy();
      }
      this.transformer = null;
    },
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    async requestcopy(shapeid)
    {
      axios.post('http://192.168.0.192:8081/copy',[shapeid, this.shapeID.toString()])
      .then (response => {
        this.shapeID++;
        console.log('Shape copied successfully:',response.data),
        console.log('shapeID', this.shapeID.toString());
        console.log('Id of copied shape', shapeid);
        this.draw_instance(response.data);
        //return response.data;
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

      let original_color;
      let original_stroke;

      //Copy Shape on Click
      this.stage.on('click', (e)=>{
        if(this.shape !== this.stage && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          if(this.shape instanceof Konva.Shape){
            this.requestcopy(this.shape.id());
          }
        }
      });
      
      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
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
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          this.shape.shadowOpacity(0)
        }
      });
    },

    async requestdelete(shapeid){
      axios.post('http://192.168.0.192:8081/delete',shapeid)
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

      //Delete Shape On-Click
      this.stage.on('click', (e)=>{
        if(this.shape !== this.stage && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          if(this.shape instanceof Konva.Shape){
            this.requestdelete(this.shape.id());
            this.shape.destroy();
            document.body.style.cursor = 'default';
            this.layer.batchDraw();
          }
        }
      });

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
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
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.strokeColor==="aliceblue")){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          this.shape.shadowOpacity(0);
        }
      });

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
    else if (shapetype==='eraser')
    {
      let pos = this.stage.getPointerPosition();
      this.pen = Line.drawLine({
      stroke: "aliceblue",
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
    background-color: #000000;
    margin: 0px 0px;
   }

    .main
    {
      display: flex;
      flex-direction: column;
      width: 100%;
      height: 100vh;
      margin: 0px 0px;
    
    }
    .tools
    {
      background-color:rgb(230, 155, 15);
      border-top-left-radius: 10px;
      border-top-right-radius: 10px;
      width: 100%;
      height: 50px;
      border-bottom: 5px solid black;
      
    }
    .board
    {
      width: 100%;
      background-color: aliceblue;
    }
    input.color{
    width: 50px; /* Set the width and height to create a square */
    height: 100%;
    padding: 5px; /* Remove padding */
    border-radius: 20px;
    border: none;
    background-color: rgb(230, 155, 15);
    }
    .shapes
    {
      margin-left: 30px;
      display: flex;
      flex-direction: row;
      height: 100%;
    }
    .A{
      display: flex;
      flex-direction: row;
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
      font-weight: bold;
      box-shadow: none;
      outline: none;
      padding-right: 50px;
      background: none;
      border: none;
      font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
      font-size: larger;

    }
    .B{
      margin-left: 15px;
      display: flex;
      flex-direction: row;
      font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
      font-size: medium;
      justify-content: space-between ;
    }
    select
    {
      outline: none;
      padding: 10px;
      border: none;
      margin-left: 30px;
      background-color: rgb(230, 155, 15);
      font-family:'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
      font-weight: bold;
      font-size: larger;
    
      
    }

    button:hover
    {
      color:white;
    }
    span
    {
      font-size: larger;    
    }
    svg :hover
    {
      fill: rgb(230, 88, 164);
    }

  .size-picker input{
    width: 95%;
    
  } 
  </style>