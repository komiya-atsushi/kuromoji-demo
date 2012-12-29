package controllers;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;
import org.atilika.kuromoji.Tokenizer.Mode;
import play.Logger;
import play.Routes;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.ArrayList;
import java.util.List;

public class Application extends Controller {
    public static Result index() {
        return ok(index.render(""));
    }

    public static Result jsRoutes() {
        response().setContentType("text/javascript");
        return ok(Routes.javascriptRouter("jsRoutes", routes.javascript.Application.tokenize()));
    }

    public static Result tokenize(String text, String segmentationMode) {
        Mode mode;
        switch (segmentationMode) {
            case "normal":
                mode = Mode.NORMAL;
                break;
            case "search":
                mode = Mode.SEARCH;
                break;
            case "extended":
                mode = Mode.EXTENDED;
                break;
            default:
                return notFound();
        }

        Logger.info(mode.toString());

        TokenizeResult result = new TokenizeResult(text, mode);
        return ok(Json.toJson(result));
    }

    public static class TokenizeResult {
        public String text;

        public List<_Token> tokens = new ArrayList<>();

        public double time;

        public TokenizeResult(String text, Mode mode) {
            long start = System.currentTimeMillis();
            Tokenizer tokenizer = Tokenizer.builder().mode(mode).build();

            for (Token token : tokenizer.tokenize(text)) {
                tokens.add(new _Token(token));
            }

            this.text = text;
            long end = System.currentTimeMillis();

            time = (end - start) / 1000.0;
        }

        public static class _Token {
            public String surfaceForm;

            public String allFeatures;

            public String partOfSpeech;

            public String baseForm;

            public _Token(Token token) {
                surfaceForm = token.getSurfaceForm();
                allFeatures = token.getAllFeatures();
                partOfSpeech = token.getPartOfSpeech();
                baseForm = token.getBaseForm();
            }
        }
    }
}