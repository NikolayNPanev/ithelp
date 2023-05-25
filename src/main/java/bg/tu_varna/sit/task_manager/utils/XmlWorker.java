package bg.tu_varna.sit.task_manager.utils;

import bg.tu_varna.sit.task_manager.data.Storage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;

public class XmlWorker {
    public static void writeToXMLFile(String xmlFile, Storage storage) throws JAXBException {

        // Създаване на JAXB контекст
        JAXBContext context = JAXBContext.newInstance(Storage.class);
        // Създаване на marshaller инстанция
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        // Записване във файл
        m.marshal(storage, new File(xmlFile));
    }

    public static Storage readerFromXMLFile(String xmlFile)
            throws JAXBException, FileNotFoundException, UnsupportedEncodingException {

        // Създаване на JAXB контекст
        JAXBContext context = JAXBContext.newInstance(Storage.class);
        // Създаване на unmarshaller инстанция
        Unmarshaller um = context.createUnmarshaller();
        InputStream inputStream = new FileInputStream(xmlFile);
        Reader reader = new InputStreamReader(inputStream, "UTF-8");

        Storage storage = (Storage) um.unmarshal(reader);

        return storage;
    }

}
