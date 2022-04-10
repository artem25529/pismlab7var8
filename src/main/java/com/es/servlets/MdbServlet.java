package com.es.servlets;

import com.es.service.Producer;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/mdb")
public class MdbServlet extends HttpServlet {
    @EJB
    private Producer producer;

    private final Path PATH = Paths.get("E:\\Programs\\glassfish5\\file.txt");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        producer.sendJmsMessage(req.getParameter("msg"));
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        printText();
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    private void printText() {
        try {
            List<String> strings = Files.readAllLines(PATH);
            if (!strings.isEmpty()) {
                String text = strings.get(0);
                if (text.length() >= 4) {
                    System.out.println("String with 4 or more chars: " + text);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
