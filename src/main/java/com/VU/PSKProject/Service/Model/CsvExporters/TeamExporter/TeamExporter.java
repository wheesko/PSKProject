package com.VU.PSKProject.Service.Model.CsvExporters.TeamExporter;

import com.VU.PSKProject.Service.Mapper.TeamMapper;
import com.VU.PSKProject.Service.Model.Team.TeamCountDTO;
import com.VU.PSKProject.Service.Model.Worker.WorkerToExportDTO;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class TeamExporter {
    @Autowired
    TeamMapper teamMapper;

    public void exportToCSV(List<TeamCountDTO> teams, HttpServletResponse response) throws Exception {
        String filename = "teams.csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

        StatefulBeanToCsv<TeamCountDTO> writer = new StatefulBeanToCsvBuilder<TeamCountDTO>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        String[] headers = {"Team Name,", "Learned topic count,", "Planned topic count,", "Goal Count\n"};
        for(String s : headers)
        {
            response.getWriter().write(s );
        }
        writer.write(teams);
    }
}
