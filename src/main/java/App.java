import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.security.InvalidParameterException;
import java.util.HashMap;
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
      model.put("animals", Animal.all());
      model.put("normalAnimals", Animal.allNormal());
      model.put("endangeredAnimals", EndangeredAnimal.allEndangeredAnimals());
      model.put("templates", "/index.hbs");
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());

    get ( "/notFound",(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", request.session().attribute("message"));
      model.put("templates", "bad-request.hbs");
      return new ModelAndView(model, "bad-request.hbs");
    }, new HandlebarsTemplateEngine());

    post("/animals/new-endangered",(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String animalName = request.queryParams("animal-name");
      String animalHealth = request.queryParams("animal-health");
      String animalAge = request.queryParams("animal-age");
      EndangeredAnimal newAnimal = new EndangeredAnimal(animalName, animalHealth, animalAge);
      try {
        newAnimal.checkFields();
        newAnimal.save();
      } catch(InvalidParameterException ipe) {
        request.session().attribute("message", ipe.getMessage());
        response.redirect("/notFound");
      }
      response.redirect("/animals/" + newAnimal.getId());
      return new ModelAndView(model, "allAnimals.hbs");
    }, new HandlebarsTemplateEngine());


    // route for adding new animal form
    post("/animal/new",(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      boolean endangered = request.queryParams("endangered")!=null;
      String animalName = request.queryParams("animal-name");
      Animal newAnimal = new Animal(animalName);

      try {
          if (endangered) {
          String name = request.queryParams("name");
          EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name);
          endangeredAnimal.save();
            response.redirect("/notfound");
          } else {
          String name = request.queryParams("name");
          Animal animal = new Animal(name);
          animal.save();
         }

          newAnimal.checkFields();
           newAnimal.save();
      } catch(InvalidParameterException ipe) {
        request.session().attribute("message", ipe.getMessage());
        response.redirect("/notFound");
      }
      response.redirect("/allAnimals" + newAnimal.getId());
      return new ModelAndView(model, "index.hbs");
    }, new HandlebarsTemplateEngine());


//    post("/animal/new", (request, response) -> {
//      boolean endangered = request.queryParams("endangered")!=null;
//      if (endangered) {
//        String name = request.queryParams("name");
//        EndangeredAnimal endangeredAnimal = new EndangeredAnimal(name);
//        endangeredAnimal.save();
//      } else {
//        String name = request.queryParams("name");
//        Animal animal = new Animal(name);
//        animal.save();
//      }
//      response.redirect("/");
//      return null;
//    });
//.........................................................................................


    //route when user clicks "All Animals" or "View Animals"
    get("/allAnimals", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      model.put("sightings", Sighting.all());
      return new ModelAndView(model, "allAnimals.hbs");
    }, new HandlebarsTemplateEngine());


   // route when clicking on "Add Animal to System"
    get("/animal/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView(model, "FormA.hbs");
    }, new HandlebarsTemplateEngine());


    //route when user clicks "Post Sighting"
    get("/sighting", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("endangeredAnimals", EndangeredAnimal.all());
      return new ModelAndView(model, "sightings.hbs");
    }, new HandlebarsTemplateEngine());


//.........................................................................................

    get("/animals/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.queryParams(":id")));
      if(animal.isEndangered()) {
        animal = EndangeredAnimal.findEndangeredAnimal(Integer.parseInt(request.queryParams(":id")));
      }
      model.put("animal", animal);
      model.put("sightings", animal.getSightings());
      return new ModelAndView(model, "sightings.hbs");
    }, new HandlebarsTemplateEngine());

    post("/animals/sightings/new",(request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String sightingRanger = request.queryParams("sighting-ranger");
      int sightingAnimalId = Integer.parseInt(request.queryParams("sighting-animal-id"));
      String sightingLocation = request.queryParams("sighting-location");
      Sighting newSighting = new Sighting(sightingLocation, sightingRanger, sightingAnimalId);
      try {
        newSighting.checkFields();
        newSighting.save();
      } catch (InvalidParameterException ipe) {
        request.session().attribute("message", ipe.getMessage());
        response.redirect("/notFound");
      }
      response.redirect("/animals/" + newSighting.getAnimalId());
      return new ModelAndView(model, "layout.hbs");
    }, new HandlebarsTemplateEngine());

  }
}

