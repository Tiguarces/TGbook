import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {IconDefinition} from '@fortawesome/fontawesome-svg-core';
import {AccountType} from '../../model/AccountType';
import {IconHelper} from '../../Utils/IconHelper';

@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
    private userIsLogged: boolean;

    @ViewChild("account")
    private accountButtonRef: ElementRef | undefined;

    constructor(private iconHelper: IconHelper, private router: Router) {
        this.userIsLogged = false;
    }

    ngOnInit(): void {}

    public get userStatus(): boolean {
        return this.userIsLogged;
    }

    public getLogInIcon(): IconDefinition {
        return this.iconHelper.logIn;
    }

    public toggle(show: boolean): void {
        if (this.accountButtonRef) {
            const index = show
                ? "display:block; z-index: 1;"
                : "display: none;";
            this.accountButtonRef.nativeElement.style = index;
        }
    }

    public onClick(type: string): void {
        const loginType = AccountType.LOGIN 
                                    .toString()
                                    .toLowerCase();

        const registerType = AccountType.REGISTER
                                    .toString()
                                    .toLowerCase();

        type === "login"
            ? this.router.navigate(
                ["account"],
                {queryParams: {status: loginType}})
            : this.router.navigate(
                ["account"],
                {queryParams: {status: registerType}})
    }
}
