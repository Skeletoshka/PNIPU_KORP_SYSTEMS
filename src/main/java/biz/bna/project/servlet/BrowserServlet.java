package biz.bna.project.servlet;

import biz.bna.project.model.Appendix;
import biz.bna.project.repository.AppendixRepository;
import biz.bna.project.rowmapper.RowMapForObject;
import biz.bna.project.utils.DatabaseUtils;
import biz.bna.project.view.AppendixView;

import javax.servlet.ServletConfig;
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
import java.util.ArrayList;
import java.util.List;

public class BrowserServlet extends HttpServlet {

    private final String sqlForSearchByWord = "" +
            "SELECT a.*, " +
            "       r.repeat_count as relevance " +
            "FROM   appendix a " +
            "       INNER JOIN repeat r ON r.appendix_id = a.appendix_id " +
            "       INNER JOIN word w ON w.word_id = r.word_id " +
            "WHERE  w.word_text = ? " +
            "ORDER BY r.repeat_count DESC";

    private DatabaseUtils databaseUtils = new DatabaseUtils();
    private AppendixRepository appendixRepository = new AppendixRepository();

    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
        }catch (ServletException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        try {
            response.setCharacterEncoding("UTF-8");
            String search = request.getParameter("search");
            List<AppendixView> result;
            if(search != null && !search.isEmpty()){
                RowMapForObject<AppendixView> rowMap = new RowMapForObject<>(AppendixView.class);
                result = databaseUtils.select(rowMap, sqlForSearchByWord, search);
            }else{
                result = new ArrayList<>();
            }
            request.setAttribute("result", result);
            request.getRequestDispatcher("/pages/index.jsp").forward(request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
