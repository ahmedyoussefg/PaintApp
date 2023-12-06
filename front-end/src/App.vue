<template>
  <div class="main">
    <div class="tools">
      <ul class="shapes">
        <li><button @click="hover_element"><span style="font-size: smaller;"><fa icon="mouse-pointer" style="color: #0e0f10;" size="sm"/></span> Select</button></li>
        <li><button @click="usePencilTool"><span>&#9998;</span> Pencil</button></li>
        <li><button @click="drawLine"><span style="font-size: medium;">&#9644; </span> Line</button></li>
        <li> <button @click="drawCircle"><span>&#9679; </span> Circle</button></li>
        <li><button @click="drawRectangle"><span style="font-size: smaller;">&#9646; </span> Rectangle</button></li>
        <li> <button @click="drawSquare"><span> &#9632; </span> Square</button></li>
        <li><button @click="drawTriangle"><span style="font-size: smaller;">&#9650; </span>Triangle</button></li>
        <li><button @click="drawEllipse"><span style="font-size: smaller;">&#11052; </span>Ellipse</button></li>
        <li><button @click="erase"><span style="font-size: smaller;"><fa icon="eraser" style="color: #0e0f10;" size="sm"/></span> Eraser</button></li>
        <li><button class = "clear" @click="clear">Clear Canvas</button></li>
        <li><button class = "save" @click="save">Save as JPG</button></li>
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
            <li><button @click="copy_shape">Copy</button></li>
            <li><button @click="delete_shape">Delete</button></li>
        </ul>
      </div>
  </div>
</template>

<script>
import Konva from 'konva';
import ShapeFactory from './ShapeFactory';
import { faLessThanEqual } from '@fortawesome/free-solid-svg-icons';

export default {
  data(){
    return {
      fill_color : 'aliceblue',
      stroke_color : 'black',
      stroke_Width :2,
      shapeID: 1,
      isOverlayVisible: false,
      shape: null,
      tr: null
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
    usePencilTool() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      let isDrawing = false;
      let lastLine;

      this.stage.on('mousedown touchstart', (e) => {
      isDrawing = true;
      let pos = this.stage.getPointerPosition();
      lastLine = new Konva.Line({
        stroke: this.fill_color,
        strokeWidth: this.stroke_Width,
        lineCap: 'round', // Smooth line ends
        lineJoin: 'round', // Smooth line corners
        points: [pos.x, pos.y],
      });
      this.layer.add(lastLine);
      });

      this.stage.on('mousemove touchmove', () => {
      if (!isDrawing) {
        return;
      }
      let pos = this.stage.getPointerPosition();
      let newPoints = lastLine.points().concat([pos.x, pos.y]);
      lastLine.points(newPoints);
      this.layer.batchDraw();
      });

      this.stage.on('mouseup touchend', () => {
      isDrawing = false;
      });
    },


    drawLine() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');

      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let line;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;

          line = ShapeFactory.createShape('line', {
            points: [startx, starty],
            stroke: this.fill_color,
            strokeWidth: this.stroke_Width,
          });

          this.layer.add(line);
          this.stage.batchDraw();

          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const endx = e.evt.offsetX;
              const endy = e.evt.offsetY;
              line.points([startx, starty, endx, endy]);
              this.stage.batchDraw();
            }
          });

          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('mouseup');
            }
          });
        }
      });
    },

    drawCircle() {
        this.stage.off('mouseover');
        this.stage.off('mousedown');
        this.stage.off('mousemove');
        this.stage.off('mouseup');
        this.stage.off('mouseout');
      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let circle;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;
      
          circle = ShapeFactory.createShape('circle', {
            x: startx,
            y: starty,
            radius: 0,
            fill: this.fill_color,
            strokeWidth: this.stroke_Width,
            stroke : this.stroke_color,
            id: this.shapeID.toString()
          });

          this.shapeID++;
          
          this.layer.add(circle);
          this.stage.batchDraw();
      
          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const radius = Math.sqrt(
                Math.pow(e.evt.offsetX - startx, 2) +
                Math.pow(e.evt.offsetY - starty, 2)
              );

              circle.radius(radius);
              this.stage.batchDraw();
            }
          });
          
          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('click');
            }
          });
        }
      });
    },



    drawRectangle() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let rectangle;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;
      
          rectangle = ShapeFactory.createShape('rectangle', {
            x: startx,
            y: starty,
            width: 0,
            height: 0,
            fill: this.fill_color,
            strokeWidth : this.stroke_Width,
            stroke :this.stroke_color,
          });
          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const width = e.evt.offsetX - startx;
              const height = e.evt.offsetY - starty;
              rectangle.width(width);
              rectangle.height(height);
              this.stage.batchDraw();
            }
          });
          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('click');
            }
          });
      
          this.layer.add(rectangle);
          this.stage.batchDraw();
        }
      });
    },

    drawSquare() {
  
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');

      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let square;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;

          square = ShapeFactory.createShape('rectangle', {
            x: startx,
            y: starty,
            width: 0,
            height: 0,
            fill: this.fill_color,
            strokeWidth : this.stroke_Width,
            stroke :this.stroke_color,
          });

          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const width = e.evt.offsetX - startx;
              const height = e.evt.offsetY - starty;
              const side = Math.min(Math.abs(width), Math.abs(height));

         
              square.width(side * Math.sign(width));
              square.height(side * Math.sign(height));

              this.stage.batchDraw();
            }
          });

          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('mouseup');
            }
          });

          this.layer.add(square);
          this.stage.batchDraw();
        }
      });
    },

    drawTriangle() {
      // Remove previous event listeners
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');

      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let triangle;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;

          triangle = new Konva.RegularPolygon({
            x: startx,
            y: starty,
            sides: 3,
            radius: 0,
            fill: this.fill_color,
            strokeWidth : this.stroke_Width,
            stroke :this.stroke_color,
          });

          this.layer.add(triangle);
          this.stage.batchDraw();

          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const side = Math.sqrt(
              Math.pow(e.evt.offsetX - startx, 2) +
              Math.pow(e.evt.offsetY - starty, 2)
              );

              // Set the radius of the triangle to determine its size
              triangle.radius(side);
              this.stage.batchDraw();
            }
          });

          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('mouseup');
            }
          });
        }
      });
    },

    drawEllipse() {
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      let startx = 0;
      let starty = 0;
      let isDrawing = false;
      let ellipse;

      this.stage.on('mousedown', (e) => {
        if (!isDrawing) {
          isDrawing = true;
          startx = e.evt.offsetX;
          starty = e.evt.offsetY;

          ellipse = ShapeFactory.createShape('ellipse', {
            x: startx,
            y: starty,
            radiusX: 0,
            radiusY: 0,
            fill: this.fill_color,
            strokeWidth : this.stroke_Width,
            stroke :this.stroke_color,
          });

          this.layer.add(ellipse);
          this.stage.batchDraw();

          this.stage.on('mousemove', (e) => {
            if (isDrawing) {
              const radiusX = Math.abs(e.evt.offsetX - startx);
              const radiusY = Math.abs(e.evt.offsetY - starty);

              ellipse.radiusX(radiusX);
              ellipse.radiusY(radiusY);
              this.stage.batchDraw();
            }
          });

          this.stage.on('mouseup', () => {
            if (isDrawing) {
              isDrawing = false;
              this.stage.off('mousemove');
              this.stage.off('click');
            }
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
      this.stroke_Width = event.target.value;
    },

    erase(){
      this.fill_color = "aliceblue";
      this.usePencilTool();
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

    hover_element(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout'); 

      let original_color;
      let original_stroke;
      this.stage.on('click', (e) => {
        const clickedShape = e.target;
        if(clickedShape !== this.stage){
          if (this.tr) {
              if(clickedShape instanceof Konva.Shape){
                this.tr.nodes([clickedShape]);
                this.tr.moveToTop();
              } else{
                this.tr.destroy();
              }
          }

          if (clickedShape instanceof Konva.Shape) {
              if (!this.tr) {
                  this.tr = new Konva.Transformer({
                      nodes: [clickedShape],
                      centeredScaling: true,
                  });
                  this.layer.add(this.tr);
              }
          }
        }else{
          if(this.tr){
            this.tr.nodes([]);
          }
        }
      });

      this.stage.on('mouseover', (e) => {
        this.shape = e.target;
        if(this.shape instanceof Konva.Shape){
          // Change Color
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
            this.shape.setStroke('#49FF43')
          }
          // Add Shadow
          this.shape.shadowColor('black');
          this.shape.shadowBlur(10);
          this.shape.shadowOffset({ x: 8, y: 8  });
          this.shape.shadowOpacity(0.7);
          
          document.body.style.cursor = 'move';
          this.shape.draggable(true);
          
          console.log(this.shape);

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

    //////////////////////////////////////////////////////////
    copy_shape(){
      console.log("copying shape");
    },


    delete_shape(){
      this.stage.off('mouseover');
      this.stage.off('mousedown');
      this.stage.off('mousemove');
      this.stage.off('mouseup');
      this.stage.off('mouseout');
      
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
          
          //Delete Shape On click
          this.shape.on('click', ()=>{
            if(this.tr){
              this.shape.destroy();
              this.tr.destroy();
              this.tr = null;
              document.body.style.cursor = 'default';
            }
            else{
              this.shape.destroy();
              document.body.style.cursor = 'default';
            }
          });
          this.layer.batchDraw();
        }
      });

      this.stage.on('mouseout', (e) => {
        document.body.style.cursor = 'default';
        if(this.shape instanceof Konva.Shape){
          this.shape.fill(original_color);
          this.shape.setStroke(original_stroke);
        }
      })
    },


    ///////////////////////////////////////////////////////////
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
    }
  }
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
    cursor: pointer;
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

.overlay{
  border: 1px solid black;
  border-radius: 20px;
  background-color: pink;
}
.clear{
  color: #737373;
  border: 1px solid #ff99f3;
  border-radius: 30px;
}

.clear:hover{
  color: #c4c4c4;
  background-color: #656565;
}

</style>