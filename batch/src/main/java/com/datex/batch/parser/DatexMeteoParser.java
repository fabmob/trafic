package com.datex.batch.parser;

import com.datex.batch.model.MeteoMeasure;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Stack;

@Component
public class DatexMeteoParser {
    private final DatexMeteoParserHandler handler;

    public DatexMeteoParser() {
        handler = new DatexMeteoParserHandler();
    }

    public ArrayList<MeteoMeasure> parseXml(InputStream in) {
        //Create a empty link of users initially
        ArrayList<MeteoMeasure> list = new ArrayList<MeteoMeasure>();
        try {

            //Create parser from factory
            XMLReader parser = XMLReaderFactory.createXMLReader();

            //Register handler with parser
            parser.setContentHandler(handler);

            //Create an input source from the XML input stream
            InputSource source = new InputSource(in);

            //parse the document
            parser.parse(source);

            //populate the parsed users list in above created empty list; You can return from here also.
            list = handler.getList();

        } catch (SAXException e) {
            if (in == null) System.err.println("IN IS NULL");
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;
    }

}

class DatexMeteoParserHandler extends DefaultHandler {

    private String publicationTime = "unknown";
    private boolean dataError = false;
    private StringBuilder path = new StringBuilder();


    // This is the list which shall be populated while parsing the XML.
    private ArrayList<MeteoMeasure> list = new ArrayList<MeteoMeasure>();

    // As we read any XML element we will push that in this stack
    private Stack<String> elementStack = new Stack<String>();

    // As we complete one user block in XML, we will push the User instance in
    // userList
    private Stack<MeteoMeasure> objectStack = new Stack<MeteoMeasure>();

    public void startDocument() throws SAXException {
        // System.out.println("start of the document : ");
    }

    public void endDocument() throws SAXException {
        // System.out.println("end of the document document : ");
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Push it in element stack
        path.append(qName);

        this.elementStack.push(qName);
        // If this is start of 'user' element then prepare a new User instance and push
        // it in object stack
        if ("D2LogicalModel:siteMeasurements".equals(qName)) {
            // New User instance
            MeteoMeasure measure = new MeteoMeasure();
            measure.setMeasurementTime(publicationTime);
            this.objectStack.push(measure);
        }
        if (!this.objectStack.empty()) {
            MeteoMeasure m = (MeteoMeasure) this.objectStack.peek();

            if ("D2LogicalModel:measurementSiteReference".equals(qName) && attributes != null && attributes.getLength() == 3) {
                m.setId(attributes.getValue(0));
            }
        }
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        // Remove last added element
        String last = this.elementStack.pop();
        if (path.length() >= last.length())
            path.delete(path.length() - last.length(), path.length());

        if (!"D2LogicalModel:dataError".equals(last))
            dataError = false;
        // User instance has been constructed so pop it from object stack and push in
        // userList
        if ("D2LogicalModel:siteMeasurements".equals(qName)) {
            MeteoMeasure measure = this.objectStack.pop();
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
        if ("D2LogicalModel:publicationTime".equals(currentElement())) {
            publicationTime = value;
        }
        if (!this.objectStack.empty()) {
            MeteoMeasure m = (MeteoMeasure) this.objectStack.peek();
            // handle the value based on to which element it belongs
            if (!dataError && path.indexOf("D2LogicalModel:humidity") > -1 && "D2LogicalModel:percentage".equals(currentElement())) {
                try {
                    m.setHumidity(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setHumidity(null);
                }
            } else if (!dataError && "D2LogicalModel:noPrecipitation".equals(currentElement())) {
                m.setNoPrecipitation(value);
            } else if (!dataError && path.indexOf("D2LogicalModel:precipitationIntensity") > -1 && "D2LogicalModel:millimetresPerHourIntensity".equals(currentElement())) {
                try {
                    m.setMillimetresPerHourIntensity(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setMillimetresPerHourIntensity(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:roadSurfaceTemperature") > -1 && "D2LogicalModel:temperature".equals(currentElement())) {
                try {
                    m.setRoadSurfaceTemperature(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setRoadSurfaceTemperature(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:temperatureBelowRoadSurface") > -1 && "D2LogicalModel:temperature".equals(currentElement())) {
                try {
                    m.setTemperatureBelowRoadSurface(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setTemperatureBelowRoadSurface(null);
                }
            } else if (!dataError && "D2LogicalModel:weatherRelatedRoadConditionType2".equals(currentElement())) {
                m.setWeatherRelatedRoadConditionType(value);
            } else if (!dataError && path.indexOf("D2LogicalModel:airTemperature") > -1 && "D2LogicalModel:temperature".equals(currentElement())) {
                try {
                    m.setAirTemperature(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setAirTemperature(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:dewPointTemperature") > -1 && "D2LogicalModel:temperature".equals(currentElement())) {
                try {
                    m.setDewPointTemperature(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setDewPointTemperature(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:windSpeed") > -1 && "D2LogicalModel:speed".equals(currentElement())) {
                try {
                    m.setWindSpeed(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setWindSpeed(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:maximumWindSpeed") > -1 && "D2LogicalModel:speed".equals(currentElement())) {
                try {
                    m.setMaximumWindSpeed(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setMaximumWindSpeed(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:windDirectionBearing") > -1 && "D2LogicalModel:directionBearing".equals(currentElement())) {
                try {
                    m.setWindDirectionBearing(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    m.setWindDirectionBearing(null);
                }
            } else if (!dataError && path.indexOf("D2LogicalModel:siteMeasurementsExtended") > -1 && "D2LogicalModel:statusType".equals(currentElement())) {
                m.setStatusType(value);
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
    public ArrayList<MeteoMeasure> getList() {
        return list;
    }
}

