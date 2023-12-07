import Konva from 'konva';

class ShapeFactory {
  static createShape(type, options) {
    switch (type) {
      case 'line':
        return new Konva.Line(options)  
      case 'circle':
        return new Konva.Circle(options);
      case 'rectangle':
        return new Konva.Rect(options);
      case 'ellipse':
        return new Konva.Ellipse(options);
      case 'triangle':
        return new Konva.RegularPolygon({
          ...options,
          sides: 3,
        });
    }
  }
}

export default ShapeFactory;