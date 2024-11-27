package lukespetitions.demo;


import lukespetitions.demo.Petition; // Import the Petition class
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.PathVariable;
import javax.annotation.PostConstruct;




import java.util.ArrayList;
import java.util.List;

@Controller
public class PetitionController {

    private List<Petition> petitions = new ArrayList<>(); // In-memory list of petitions

    // Page to create a petition
    @GetMapping("/create")
    public String showCreatePetitionPage() {
        return "createPetitions"; // This renders the createPetition.html template
    }

    // Handling the form submission for creating a petition
    @PostMapping("/create")
    public String createPetition(
            @RequestParam("title") String title,
            @RequestParam("description") String description) {
        Petition newPetition = new Petition(petitions.size() + 1, title, description);
        petitions.add(newPetition);
        return "redirect:/viewAll";  // Use relative path
    }

    // Page to view all petitions
    @GetMapping("/viewAll")
    public String showAllPetitions(Model model) {
        model.addAttribute("petitions", petitions);
        return "viewAllPetitions"; // This renders the viewAllPetitions.html template
    }
    @GetMapping("/search")
    public String showSearchPage() {
        return "searchPetition"; // Renders searchPetition.html
    }

    @GetMapping("/searchResults")
    public String searchPetitions(@RequestParam("title") String title, Model model) {
        List<Petition> results = petitions.stream()
                .filter(petition -> petition.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("results", results);
        return "searchResults"; // Renders searchResults.html
    }
    // View petition details
    @GetMapping("/petition/{id}")
    public String viewPetition(@PathVariable("id") int id, Model model) {
        Petition petition = petitions.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("petition", petition);
        return "viewPetition"; // Renders viewPetition.html
    }

    // Handle signing a petition
    @PostMapping("/petition/{id}/sign")
    public String signPetition(@PathVariable("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam("email") String email) {
        // Print the petition signing details
        System.out.println("Petition " + id + " signed by " + name + " (" + email + ")");
        return "redirect:/viewAll"; // Redirect to view all petitions
    }

    @PostConstruct
    public void init() {
        petitions.add(new Petition(1, "Save the Rainforest", "Help us protect the rainforest."));
        petitions.add(new Petition(2, "Reduce Plastic Waste", "Join us in reducing plastic waste."));
        petitions.add(new Petition(3, "Support Clean Energy", "Sign this petition to support clean energy."));

    }
    @GetMapping("/")
    public String showLandingPage() {
        return "index"; // Renders index.html as the default landing page
    }

}

