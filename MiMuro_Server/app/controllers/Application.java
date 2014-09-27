package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
//        return ok(index.render("Your new application is ready."));
        return redirect("/app/index.html");
    }

    // CORS
    public static Result rootOptions() {
        return noContent();
    }

    public static Result options(String url) {
        return noContent();
    }
    // fin CORS.

}
