package model;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import model.transports.Transport;

import java.io.File;

public class JaxbWorker {
    // восстанавливаем объект из XML файла
    protected static Transport fromXmlToObject(String filePath) {
        try {
            // создаем объект JAXBContext - точку входа для JAXB
            JAXBContext jaxbContext = JAXBContext.newInstance(Transport.class);
            Unmarshaller un = jaxbContext.createUnmarshaller();

            return (Transport) un.unmarshal(new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    // сохраняем объект в XML файл
    protected static void convertObjectToXml(Transport transport, String filePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(Transport.class);
            Marshaller marshaller = context.createMarshaller();
            // устанавливаем флаг для читабельного вывода XML в JAXB
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // маршаллинг объекта в файл
            marshaller.marshal(transport, new File(filePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}