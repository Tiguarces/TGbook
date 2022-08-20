import {Injectable} from "@angular/core";
import {IconDefinition} from "@fortawesome/fontawesome-svg-core";
import {faGithub} from "@fortawesome/free-brands-svg-icons";
import {faArrowRight, faPlus} from "@fortawesome/free-solid-svg-icons";

@Injectable()
export class IconHelper {
    private gitHubIcon: IconDefinition;
    private logInIcon: IconDefinition;
    private plusIcon: IconDefinition;

    constructor() {
       this.plusIcon = faPlus;
       this.gitHubIcon = faGithub;
       this.logInIcon = faArrowRight;
    }

    
    public get plus(): IconDefinition {
        return this.plusIcon;
    }


    public get gitHub(): IconDefinition {
        return this.gitHubIcon;
    }

    
    public get logIn(): IconDefinition {
        return this.logInIcon;
    }

}
