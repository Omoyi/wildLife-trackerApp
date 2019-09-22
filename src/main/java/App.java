import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.Route;

import static spark.Spark.*;
import java.security.InvalidParameterException;

public class App {
  public static void main(String[] args) {
    String layout = "templates/layout.vtl";
    staticFileLocation("/public");

    get("/", (Route) (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("animals", Animal.all());
      model.put("normalAnimals", Animal.allNormal());
      model.put("endangeredAnimals", EndangeredAnimal.allEndangeredAnimals());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/notFound", (Route) (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("message", request.session().attribute("message"));
      model.put("template", "templates/bad-request.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/new-endangered", (Route) (request, response) -> {
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
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/new", (Route) (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String animalName = request.queryParams("animal-name");
      Animal newAnimal = new Animal(animalName);
      try {
        newAnimal.checkFields();
        newAnimal.save();
      } catch(InvalidParameterException ipe) {
        request.session().attribute("message", ipe.getMessage());
        response.redirect("/notFound");
      }
      response.redirect("/animals/" + newAnimal.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/animals/:id", (Route) (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Animal animal = Animal.find(Integer.parseInt(request.params(":id")));
      if(animal.isEndangered()) {
        animal = (EndangeredAnimal) EndangeredAnimal.findEndangeredAnimal(Integer.parseInt(request.params(":id")));
      }
      model.put("animal", animal);
      model.put("sightings", animal.getSightings());
      model.put("template", "templates/animal-sightings.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/animals/:id/sightings/new", (Route) (request, response) -> {
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
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
