package com.VU.PSKProject.Service.Model.CsvExporters.WorkerExporter;

import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Mapper.WorkerMapper;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToGetDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerExporter {

    @Autowired
    WorkerMapper workerMapper;

    public void exportToCSV(List<WorkerToExportDTO> workers, HttpServletResponse response) throws Exception {
        String filename = "workers.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<WorkerToExportDTO> writer = new StatefulBeanToCsvBuilder<WorkerToExportDTO>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        String[] headers = {"Name,", "Surname,", "Email,", "Role,", "Team\n"};
        for(String s : headers)
        {
            response.getWriter().write(s );
        }
        writer.write(workers);
    }
}
