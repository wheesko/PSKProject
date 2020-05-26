package com.VU.PSKProject.Service.CSVExporter;

import java.util.List;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
public class CSVExporter {
    public static  <T> void buildExportToCSVResponse(List<T> listToExport, String[] headers,
                                   HttpServletResponse response) throws Exception {
        String filename = "exportedData.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<T> writer = new StatefulBeanToCsvBuilder<T>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        for(String s : headers)
        {
            response.getWriter().write(s);
        }
        writer.write(listToExport);
    }
}
