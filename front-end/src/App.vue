<template>
    <div class="main">
      <div class="tools">
        <div class="shapes">
          <button v-if="!clickedsel" @click="hover_element(); clickedsel = true ;reset_selection();"><span style="font-size: x-large" v-tooltip.bottom="'Select'"><font-awesome-icon :icon="['fas', 'arrow-pointer']" /></span> </button>
          <button v-if="clickedsel" style="color:#f0f8ff;" @click="hover_element(); clickedsel = false ; reset_selection();"><span style="font-size: x-large" ><font-awesome-icon :icon="['fas', 'arrow-pointer']" /></span> </button>         
          <div class="A">
            <button v-if="!clickedcircle"  @click="drawshape('circle'); reset_selection();" v-tooltip.bottom="'Circle'"> <span style="font-size: x-large; font-weight: bold;"> &#9711;</span> </button>
            <button style="color: #f0f8ff;" v-if="clickedcircle"  @click="drawshape('circle'); reset_selection();" v-tooltip.bottom="'Circle'"> <span style="font-size: x-large; font-weight: bold;"> &#9711;</span> </button>
            <button v-if="!clickedrec" @click="drawshape('rectangle'); reset_selection();" v-tooltip.bottom="'Rectangle'"><span style="font-size: xx-large;">&#9645;</span></button>
            <button v-if="clickedrec"  style="color: #f0f8ff;" @click="drawshape('rectangle'); reset_selection();" v-tooltip.bottom="'Rectangle'"><span style="font-size: xx-large;">&#9645;</span></button>
            <button v-if="!clickedsq" @click="drawshape('square'); reset_selection();" v-tooltip.bottom="'Square'"><span style="font-size: xx-large;">&#9723; </span></button>
            <button v-if="clickedsq"  style="color: #f0f8ff;" @click="drawshape('square'); reset_selection();" v-tooltip.bottom="'Square'"><span style="font-size: xx-large;">&#9723; </span></button>
            <button v-if="!clickedtr" @click="drawshape('triangle'); reset_selection();" v-tooltip.bottom="'Triangle'"><span style="font-size: xx-large;">&#9651;</span></button>
            <button v-if="clickedtr"  style="color: #f0f8ff;" @click="drawshape('triangle'); reset_selection();" v-tooltip.bottom="'Triangle'"><span style="font-size: xx-large;">&#9651;</span></button>
            <button v-if="!clickedellipse" class="elip" @click="drawshape('ellipse'); reset_selection();" v-tooltip.bottom="'Ellipse'"><span style="font-size: xx-large;">&#11053;</span> </button>
            <button v-if="clickedellipse" style="color: #f0f8ff;" class="elip" @click="drawshape('ellipse'); reset_selection();" v-tooltip.bottom="'Ellipse'"><span style="font-size: xx-large;">&#11053;</span> </button>
            <button v-if="!clickedline" @click="drawshape('line'); reset_selection();" v-tooltip.bottom="'Line'"><span style="font-size: xx-large;">/</span></button>
            <button v-if="clickedline"  style="color: #f0f8ff;" @click="drawshape('line'); reset_selection();" v-tooltip.bottom="'Line'"><span style="font-size: xx-large;">/</span></button>
            <button v-if="!clickedpen" @click="drawshape('pencil'); reset_selection();" v-tooltip.bottom="'Pencil'"><span style="font-size: xx-large;">&#9998;</span> </button>
            <button v-if="clickedpen"  style="color: #f0f8ff;" @click="drawshape('pencil'); reset_selection();" v-tooltip.bottom="'Pencil'"><span style="font-size: xx-large;">&#9998;</span> </button>
          </div>

          <div style="margin-left: 10px;" class="A">
            <button v-if="!clickeder" v-tooltip.bottom="'Eraser'" @click="drawshape('eraser'); reset_selection()"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'eraser']" /></span></button>
            <button v-if="clickeder" style="color: #f0f8ff;" v-tooltip.bottom="'Eraser'" @click="drawshape('eraser'); reset_selection()"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'eraser']" /></span></button>
            <button v-tooltip.bottom="'Clear'" class = "clear" @click="clear(); reset_selection()"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'broom']" /></span> </button>
          </div>

          <div class="B">
            <!-- <label for="colorfill">Fill Color</label> -->
            <button v-if="!clickedbuc" v-tooltip.bottom="'Fill'" @click="bucket_fill();clickedbuc = true;reset_selection();"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'fill']" /></span></button>
            <button v-if="clickedbuc" style="color: #f0f8ff;" v-tooltip.bottom="'Fill'" @click="bucket_fill();clickedbuc=false;reset_selection();"><span style="font-size: x-large;"><font-awesome-icon :icon="['fas', 'fill']" /></span></button>
            <input class="color" v-tooltip.bottom="'Fill Picker'" v-model="fill_color"  type="color" id="colorfill">
            <input class="color" v-tooltip.bottom="'Stroke'" v-model="stroke_color"  type="color" id="colorstroke">
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
            <button v-tooltip.bottom="'Undo'" @click="Undo();reset_selection()"><span style="font-size: xx-large;">&#8617;</span> </button>
            <button v-tooltip.bottom="'Redo'" @click="Redo();reset_selection()"><span style="font-size:xx-large;">&#8618;</span> </button>
          </div>

          <div class="A flex items-center justify">
            <button v-if="!clickedcp" v-tooltip.bottom="'Copy'" @click="copy_shape(); clickedcp = true;reset_selection()"><span style="font-size: larger;"><font-awesome-icon :icon="['fas', 'copy']" /></span></button>
            <button v-if="clickedcp" style="color: #f0f8ff;" v-tooltip.bottom="'Copy'" @click="copy_shape(); clickedcp = false;reset_selection()"><span style="font-size: larger;"><font-awesome-icon :icon="['fas', 'copy']" /></span></button>
            <button v-if="!clickeddl" v-tooltip.bottom="'Delete'" @click="delete_shape(); clickeddl=true;reset_selection();"><span style="font-size: larger;"><font-awesome-icon :icon="['fas', 'trash-can']" /></span></button>
            <button v-if="clickeddl" style="color: #f0f8ff;" v-tooltip.bottom="'Delete'" @click="delete_shape(); clickeddl = false;reset_selection();"><span style="font-size: larger;"><font-awesome-icon :icon="['fas', 'trash-can']" /></span></button>
            <input ref="fileInputXML" type="file" id="fileInput" @change="loadXML" style="display: none;" />
            <input ref="fileInputJSON" type="file" id="fileInput" @change="loadJSON" style="display: none;" />
            <button @click="toggleSave" v-tooltip.bottom="'Save'"><span class="pi pi-save"></span></button>
            <Menu id="overlay_menu" ref="Savemenu" :model="saveitems" :popup="true" />
            <button @click="toggleLoad" v-tooltip.bottom="'Load'"><span class="pi pi-upload"></span></button>
            <Menu id="overlay_menu" ref="Loadmenu" :model="loaditems" :popup="true" />

          </div>          
        </div><br>
          
      </div>
      <div class="board" ref="stageContainer"></div>
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
import { saveAs } from 'file-saver';

export default {
  data(){
    return {
      saveitems: [
        {
          label: 'Save JSON',
          command: () => {
            this.saveAsJSON();
          },
        },
        {
          label: 'Save XML',
          command: () => {
            this.saveAsXML();
          },
        },
      ],
      loaditems: [
        {
          label: 'Load JSON',
          command: () => {
            this.triggerFileInputJSON();
          },
        },
        {
          label: 'Load XML',
          command: () => {
            this.triggerFileInputXML();
          },
        },
      ],

      fill_color : '#f0f8ff',
      stroke_color : '#000000',
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
      clickedsel: false,
      clickedcircle :false,
      clickedrec :false,
      clickedsq :false,
      clickedtr :false,
      clickedellipse :false,
      clickedline :false,
      clickedpen :false,
      clickeder: false,
      clickedbuc: false,
      clickeddl: false,
      clickedcp: false
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

    axios.get('http://localhost:8081/board') // Returning the promise
    .then(response => {
        this.board = response.data;
        this.drawBoard(this.board);
    })
    .catch(error => {
      console.log('error retrive the canvas');
      throw error;
    });
},

  methods: {
    toggleSave(event) {
      this.$refs.Savemenu.toggle(event);
    },
    toggleLoad(event) {
      this.$refs.Loadmenu.toggle(event);
    },
    Load()
    {
      this.showModalload = true
    },
    loadJSON(event)
    {
      this.showModalload = false;
      this.readFileJSON(event)
    },

    Save() {
      this.showModalSave = true;
    },
    loadXML(event){
      this.showModalload = false;
      this.readFileXML(event);
    },
    saveAsJSON() {
      this.fetchAndSaveData();
      this.showModalSave = false;
    },
    saveAsXML(){
      this.fetchAndSaveXml();
      this.showModalSave = false;
    },
async  fetchAndSaveData() {
  try {
    const response = await axios.get('http://localhost:8081/saveJSON');
    const jsonData = response.data;
    const jsonString = JSON.stringify(jsonData, null, 2);

    const opts = {
      types: [
        {
          description: 'JSON file',
          accept: { 'application/json': ['.json'] },
        },
      ],
    };

    const fileHandle = await window.showSaveFilePicker(opts);
    const writableStream = await fileHandle.createWritable();

    const encoder = new TextEncoder();
    const data = encoder.encode(jsonString);

    await writableStream.write(data);
    await writableStream.close();

    console.log('File saved successfully!');
  } catch (error) {
    console.error('Error fetching or saving data:', error);
  }
},

    async fetchAndSaveXml() {
      try {
        const response = await axios.get('http://localhost:8081/saveXML', { responseType: 'text' });
        const xmlString = response.data;
        const opts = {
      types: [
        {
          description: 'XML file',
          accept: { 'application/xml': ['.xml'] },
        },
      ],
    };

    const fileHandle = await window.showSaveFilePicker(opts);
    const writableStream = await fileHandle.createWritable();

    const encoder = new TextEncoder();
    const data = encoder.encode(xmlString);

    await writableStream.write(data);
    await writableStream.close();

    console.log('File saved successfully!');
  } catch (error) {
    console.error('Error fetching or saving data:', error);
  }
    },
    triggerFileInputXML() {
      // Trigger a click on the hidden file input
      this.$refs.fileInputXML.click();
    },
    triggerFileInputJSON() {
      // Trigger a click on the hidden file input
      this.$refs.fileInputJSON.click();
    },
    async requestXMLLoader(shapeData)
    {
      console.log(encodeURIComponent(shapeData));
      axios.post('http://localhost:8081/loadXML', encodeURIComponent(shapeData))
      .then (response => {
        this.board = response.data;
        console.log('Shape saved successfully:',response.data);
        this.drawBoard(this.board);
        return this.board;
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });
    },
    readFileXML(event) {
      this.showModalload = false;

      const fileInput = event.target;      
      if (fileInput.files.length > 0) {
        const selectedFile = fileInput.files[0];

        const reader = new FileReader();

        reader.onload = event => {
          const fileContents = event.target.result;
          this.requestXMLLoader(fileContents);
          console.log('File contents:', fileContents);
        };
        reader.readAsText(selectedFile);
      } else {
        console.error('No file selected');
      }
    },
  readFileJSON(event) {
    console.log("redFile Json INSDE");
        const fileInput = event.target;
    if (fileInput.files.length > 0) {
      const selectedFile = fileInput.files[0];

      const reader = new FileReader();

      reader.onload = event => {
        const fileContents = event.target.result;

        try {
          const shapesArray = JSON.parse(fileContents);
          axios.post('http://localhost:8081/loadJSON',shapesArray)
          .then (response => {
            this.board = response.data;
            this.drawBoard(this.board);

            console.log('Shape saved successfully:',response.data)
            return this.board;
          })
          .catch (error => {
            console.error('Error saving shape:' , error);
          });
        } catch (error) {
          console.error('Error parsing JSON:', error);
        }
      };
      reader.readAsText(selectedFile);
    } else {
      console.error('No file selected');
    }
  },
    async requestdraw(shapeData)
    {
      axios.post('http://localhost:8081/draw',shapeData)
      .then (response => {
        console.log('Shape saved successfully:',response.data.id);
      })
      .catch (error => {
        console.error('Error saving shape:' , error);
      });
    },
    async requestundo() {
    return axios.post('http://localhost:8081/undo') // Returning the promise
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
      return axios.post('http://localhost:8081/redo') // Returning the promise
    .then(response => {
      this.board = response.data;
      return this.board; 
    })
    .catch(error => {
      console.error('Error receiving shapes', error);
      throw error; 
    });
    },

  drawBoard(boardData) {
  this.layer.removeChildren();
  for (let i = 0; i < boardData.length; i++) {
    console.log(boardData[i]);
    this.draw_instance(boardData[i]);
  }
},
async requestClear(){
  return axios.get('http://localhost:8081/clear') // Returning the promise
    .then(response => {
        console.log('cleared the canvas');
    })
    .catch(error => {
      console.log('error clearing the canvas');
      throw error;
    });
},
 requestBoard(){
  return axios.get('http://localhost:8081/board') // Returning the promise
    .then(response => {
        console.log('retrive the canvas');
        this.board = response;
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
      this.drawBoard(boardData); // Call undo() with the updated board data
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
      this.drawBoard(boardData); // Call undo() with the updated board data
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
      this.stage.off('transformend');
      this.stage.off('dragend');

      let original_color;
      let original_stroke;

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
          this.shape.shadowColor('#000000');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          document.body.style.cursor = 'move';
          this.shape.draggable(true);

          this.layer.batchDraw();
        }
      });

this.stage.on('click', (e) => {
  const clickedShape = e.target;
  console.log(e);

  // Check if the clicked shape is not the stage itself
  // and it's not a specific type of shape you want to ignore
  if (clickedShape !== this.stage && !(clickedShape instanceof Konva.Line && clickedShape.getStroke() === '#f0f8ff')) {
    // Remove transformer from the previously selected shape
    if (this.transformer && this.shape) {
      this.transformer.detach();
      this.layer.draw();
    }

    this.shape = clickedShape;

    // Check if the clicked shape is an instance of Konva.Shape
    if (clickedShape instanceof Konva.Shape) {
      // Create a new transformer for the clicked shape
      this.transformer = new Konva.Transformer({
        nodes: [clickedShape],
        centeredScaling: true,
        ignoreStroke: true,
      });

      // Add the transformer to the layer
      this.layer.add(this.transformer);

      console.log("Hello from creating transformer");

      // Attach event listener for transformend event
      clickedShape.on('transformend', () => {
        console.log('transform ended');
        let sentShape = clickedShape;
        if (sentShape.fill() === '#FF0C59') {
          sentShape.fill(original_color);
        }

        if (sentShape.getStroke() === '#43FFF9') {
          sentShape.setStroke(original_stroke);
        }

        this.requestUpdate(sentShape);
      });
    }
  } else {
    // Hide the transformer if it exists
    if (this.transformer) {
      console.log("Hiding Transformer from stage");
      this.transformer.detach();
      this.layer.draw();
    }
  }
});
      this.stage.on('mouseout', (e) => {
        this.shape = e.target;

        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
        return axios.post('http://localhost:8081/update', dto_sent) // Returning the promise
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
    bucket_fill(){
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
      console.log("Delete", this.clickeddl);
      console.log("Copy", this.clickedcp);
      if(this.clickedsel){
        this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
      }
      if(this.clickedbuc){
        this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
      }
      if(this.clickedcp){
        this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedsel = false
      }
      if(this.clickeddl){
        this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickedcp= false
          this.clickedsel = false
      }

    },
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    async requestcopy(shapeid)
    {
      axios.post('http://localhost:8081/copy',[shapeid, this.shapeID.toString()])
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
        if(this.shape !== this.stage && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
          if(this.shape instanceof Konva.Shape){
            this.requestcopy(this.shape.id());
          }
        }
      });
      
      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
          this.shape.shadowColor('#000000');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
          this.shape.shadowOpacity(0)
        }
      });
    },

    async requestdelete(shapeid){
      axios.post('http://localhost:8081/delete',shapeid)
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
        if(this.shape !== this.stage && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
          this.shape.shadowColor('#000000');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape && !(this.shape instanceof Konva.Line && this.shape.getStroke()==="#f0f8ff")){
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
      if (shapetype === 'circle')
        {
          this.clickedcircle =true
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'ellipse')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = true
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'line')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =true
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'pencil')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =true
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'rectangle')
        {
          this.clickedcircle =false
          this.clickedrec = true
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'square')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = true
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'triangle')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = true
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= false
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }
        else if (shapetype === 'eraser')
        {
          this.clickedcircle =false
          this.clickedrec = false
          this.clickedsq = false
          this.clickedtr = false
          this.clickedellipse = false
          this.clickedline =false
          this.clickedpen =false
          this.clickeder= true
          this.clickedbuc= false
          this.clickeddl= false
          this.clickedcp= false
          this.clickedsel = false
        }

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
          strokeWidth : parseInt(this.stroke_Width),
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
        strokeWidth : parseInt(this.stroke_Width),
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
        strokeWidth : parseInt(this.stroke_Width),
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
        strokeWidth : parseInt(this.stroke_Width),
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
        strokeWidth: parseInt(this.stroke_Width),
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
      strokeWidth: parseInt(this.stroke_Width),
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
      stroke: "#f0f8ff",
      strokeWidth: parseInt(this.stroke_Width),
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
        else if (shapetype === 'rectangle')
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
        else if (shapetype === 'square')
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
        else if (shapetype === 'triangle')
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
        else if (shapetype === 'ellipse')
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
        else if (shapetype === 'line')
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
        else if (shapetype === 'pencil')
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
        else if (shapetype === 'eraser')
        {
          if(this.pen.points().length<=2)
            return;
          Shape.shapeType = 'eraser';
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
      z-index:1;
      position: fixed;
      background-color:rgb(230, 155, 15);
      border-top-left-radius: 10px;
      border-top-right-radius: 10px;
      width: 100%;
      height: 50px;
      border-bottom: 5px solid #000000;
      
    }
    .board
    {
      width: 100%;
      background-color: #f0f8ff;
    }
    input.color{
    width: 50px; /* Set the width and height to create a square */
    height: 40px;
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
      justify-content: center;
      align-items: center;
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
.overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5); /* Semi-transparent #000000 background */
  z-index: 1; /* Place it above other elements */
}
  
  
</style>