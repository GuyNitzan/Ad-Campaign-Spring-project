package me.guy;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CampaignController {

    /*private final EmployeeRepository repository;
    private final EmployeeModelAssembler assembler;*/

    /*public CampaignController(EmployeeRepository repository, EmployeeModelAssembler assembler) {

    }*/
    public CampaignController(){}

    @PostMapping("/create-campaign")
    public String createCampaign() {
        return "Congratulations from BlogController.java";
    }

    /*@PostMapping("/employees")
    ResponseEntity<?> newEmployee(@RequestBody Campaign newCampaign) {

        EntityModel<Campaign> entityModel = assembler.toModel(repository.save(newCampaign));

        return ResponseEntity //
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }*/
}
