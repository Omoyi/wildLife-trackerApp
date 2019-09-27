import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static spark.Spark.*;


public class App {


    static int getHerokuAssignedPort() {
    ProcessBuilder processBuilder = new ProcessBuilder();
    if (processBuilder.environment().get("PORT") != null) {
      return Integer.parseInt(processBuilder.environment().get("PORT"));
    }
    return 4567;
  }

  public static void main(String[] args) {

    String layout = "/layout.hbs";
    staticFileLocation("/public");
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get("/details", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView(model, "animalDetails.hbs");
    }, new HandlebarsTemplateEngine());


    get ( "/notFound",(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", request.session().attribute("message"));
      model.put("templates", "bad-request.hbs");
      return new ModelAndView(model, "bad-request.hbs");
    }, new HandlebarsTemplateEngine());

    get("/allAnimals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("sightings", Sighting.all());
      return new ModelAndView(model, "allAnimals.hbs");
    }, new HandlebarsTemplateEngine());


    get("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView(model, "FormA.hbs");
    }, new HandlebarsTemplateEngine());


    get("/sighting", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      return new ModelAndView(model, "sightings.hbs");
    }, new HandlebarsTemplateEngine());

    post("/animal/new", (request, response) -> {
      boolean endangered = request.queryParams("endangered")!=null;
      if (endangered) {
        String name = request.queryParams("name");
        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name,true);
        endangeredAnimal.save();
      } else {
        String name = request.queryParams("name");
        Animal animal = new Animal(name,false);
        animal.save();
      }
      response.redirect("/allAnimals");
    return null;
    });

    get("/animals/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.queryParams(":id")));
      model.put("animal", animal);
      model.put("sightings", animal.getSightings());
      return new ModelAndView(model, "sightings.hbs");
    }, new HandlebarsTemplateEngine());

    post("/animals/sightings/new",(request, response) -> {
              Map<String, Object> model = new HashMap<String, Object>();
              String Ranger = request.queryParams("rangerName");
              int AnimalId = Integer.parseInt(request.queryParams("animalSelected"));
              System.out.print(AnimalId);
              String sightingLocation = request.queryParams("location");
              Sighting newSighting = new Sighting(sightingLocation, Ranger, AnimalId);
              newSighting.save();
              response.redirect("/details");
              return new ModelAndView(model, "animalDetails.hbs");
            }, new HandlebarsTemplateEngine());


    get("/animals/sightings/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.queryParams(":id")));
      model.put("animal", animal);
      model.put("endangeredAnimals", animal.getSightings());
      return new ModelAndView(model, "details.hbs");
    }, new HandlebarsTemplateEngine());


      post("/endangered_sighting",(request, response) -> {
        Map<String, Object> model = new HashMap<String, Object>();
        String Ranger = request.queryParams("rangerName");
        int AnimalId = Integer.parseInt(request.queryParams("endangeredAnimalSelected"));
        String sightingLocation = request.queryParams("location");
        String health = request.queryParams("health");
        String age = request.queryParams("age");
        Sighting newSighting = new Sighting(sightingLocation,  Ranger,0);
        newSighting.save();
        response.redirect("/details");

        return new ModelAndView(model, "execution.hbs");
    }, new HandlebarsTemplateEngine());


// the codes below are not working i left them them for future use when studying on my project
//      get("/animal/:id", (request, response) -> {
//          Map<String, Object> model = new HashMap<String, Object>();
//          Animal animals = Animal.find(Integer.parseInt(request.params("animalSelected")));
//          model.put("animals", animals);
//          return new ModelAndView(model, "animalDetail.hbs");
//      }, new HandlebarsTemplateEngine());
//
////
//      get("/endangeredAnimals/:id", (request, response) -> {
//          Map<String, Object> model = new HashMap<String, Object>();
//          EndangeredAnimal endangeredAnimal = EndangeredAnimal.find(Integer.parseInt(request.params("endangeredAnimalSelected")));
//
//        assert endangeredAnimal != null;
//        List<Sighting> sightings= endangeredAnimal.getSightings();
//
//          model.put("sightings", sightings);
//
//          return new ModelAndView(model, "animalDetail.hbs");
//      }, new HandlebarsTemplateEngine());

  }
}

