package com.datex.batch.garbage;

import com.datex.batch.model.TrafficMeasure;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.Stack;

public class DatexParserHandler extends DefaultHandler {

    private String publicationTime = "unknown";

    // This is the list which shall be populated while parsing the XML.
    private ArrayList<TrafficMeasure> list = new ArrayList<TrafficMeasure>();

    // As we read any XML element we will push that in this stack
    private Stack<String> elementStack = new Stack<String>();

    // As we complete one user block in XML, we will push the User instance in
    // userList
    private Stack<TrafficMeasure> objectStack = new Stack<TrafficMeasure>();

    public void startDocument() throws SAXException {
        // System.out.println("start of the document : ");
    }

    public void endDocument() throws SAXException {
        // System.out.println("end of the document document : ");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Push it in element stack
        this.elementStack.push(qName);
        // If this is start of 'user' element then prepare a new User instance and push
        // it in object stack
        if ("siteMeasurements".equals(qName)) {
            // New User instance
            TrafficMeasure measure = new TrafficMeasure();
            measure.setMeasurementTime(publicationTime);
            this.objectStack.push(measure);
        }
        if (!this.objectStack.empty()) {
            TrafficMeasure m = (TrafficMeasure) this.objectStack.peek();

            if ("measurementSiteReference".equals(qName) && attributes != null && attributes.getLength() == 3) {
                m.setId(attributes.getValue(1));
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Remove last added element
        this.elementStack.pop();

        // User instance has been constructed so pop it from object stack and push in
        // userList
        if ("siteMeasurements".equals(qName)) {
            TrafficMeasure measure = this.objectStack.pop();
            this.list.add(measure);
        }
    }

    /**
     * This will be called everytime parser encounter a value node
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        String value = new String(ch, start, length).trim();

        if (value.length() == 0) {
            return; // ignore white space
        }
        //System.out.println(currentElement() + ":" + value);
        if ("publicationTime".equals(currentElement())) {
            publicationTime = value;
        }
        if (!this.objectStack.empty()) {
            TrafficMeasure m = (TrafficMeasure) this.objectStack.peek();
            // handle the value based on to which element it belongs
            if ("latitude".equals(currentElement())) {
                m.setLatitude(value);
            } else if ("longitude".equals(currentElement())) {
                m.setLongitude(value);
            } else if ("speed".equals(currentElement())) {
                m.setAverageVehicleSpeed(value);
            } else if ("roadNumber".equals(currentElement())) {
                m.setRoad(value);
            } else if ("vehicleFlowRate".equals(currentElement())) {
                m.setVehicleFlowRate(value);
            } else if ("directionBoundOnLinearSection".equals(currentElement())) {
                m.setDirection(value);
            } else if ("percentage".equals(currentElement())) {
                m.setTrafficConcentration(value);
            }
        }
    }

    /**
     * Utility method for getting the current element in processing
     */
    private String currentElement() {
        return this.elementStack.peek();
    }

    // Accessor for userList object
    public ArrayList<TrafficMeasure> getList() {
        return list;
    }
}
