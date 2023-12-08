package com.jetbrains.csed.paint.Services;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.jetbrains.csed.paint.Shapes.ShapeDTO;

@Service
public class XmlService {

    public String convertToXml(ArrayList<ShapeDTO> shapes) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(shapes);
    }
    public ArrayList<ShapeDTO> convertXmlToShapes(String xml) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.readValue(xml, new TypeReference<ArrayList<ShapeDTO>>() {});
    }



}
