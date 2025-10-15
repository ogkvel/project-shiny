package com.ogkvel;

import static spark.Spark.*;
import com.ogkvel.controllers.TaskController;

public class Main {
    public static void main(String[] args) {
        port(4567);
        staticFiles.location("/public");

        before((req, res) -> {
            res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            res.header("Access-Control-Allow-Headers", "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,");
        });


        com.ogkvel.controllers.TaskController taskController = new com.ogkvel.controllers.TaskController();
        taskController.setupRoutes();

        System.out.println("Server running on http://localhost:4567");
    }
}