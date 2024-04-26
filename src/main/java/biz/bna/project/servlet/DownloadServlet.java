package biz.bna.project.servlet;

import biz.bna.project.model.Appendix;
import biz.bna.project.repository.AppendixRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class DownloadServlet extends HttpServlet {

    private AppendixRepository appendixRepository = new AppendixRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        Appendix appendix = appendixRepository.getOne(Integer.parseInt(id));
        String fileName = appendix.getAppendixPath();
        response.setContentType(Files.probeContentType(Path.of(fileName)));
        response.setHeader("Content-disposition",String.format("attachment; filename=%s", appendix.getAppendixName()));
        File my_file = new File(fileName);
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(my_file);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0){
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}