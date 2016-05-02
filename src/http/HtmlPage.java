package http;

public class HtmlPage {

        public final static String PAGE1 = "<!DOCTYPE html>\n" +
                "<html>\n" +
                " <head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "  <title>POST</title>\n" +
                " </head>\n" +
                " <body>\n" +
                "  <form id=\"message\"  method=\"POST\"></form>\n" +
                "  <p>AIPOS в„–1</p>\n" +
                "  <p><input name=\"number\" form=\"message\">\n" +
                "  <input name=\"checksum\" form=\"message\">\n"+              //вот это моё
                "  <input name=\"base64Code\" form=\"message\"></p>\n" +
                "  <p><input type=\"submit\" form=\"message\"></p> \n" +
                " </body>\n" +
                "</html>";

        public final static String ERROR404 = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "    <head>\n" +
                "       <meta charset=\"utf-8\">\n" +
                "       <title>error</title>\n" +
                "    </head>\n" +
                "    <body>\n" +
                "        404 NOT FOUND\n" +
                "    </body>\n" +
                "</html>";

        public final static String ERROR200 = "\nOK\n";
}
