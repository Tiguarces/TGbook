import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {AccountType} from '../../model/AccountType';
import {Position} from '../../Utils/Position';
import {ToastrHelper} from '../../Utils/ToastrHelper';

@Component({
    selector: 'app-account',
    templateUrl: './account.component.html',
    styleUrls: ['./account.component.css']
})
export class AccountComponent implements OnInit {
    private type: string;
    private loginForm: FormGroup;
    private registerForm: FormGroup;

    constructor(private router: ActivatedRoute, private toastr: ToastrHelper) {
        this.type = "";

        this.router.queryParams.subscribe(
            params => this.type = this.checkType(params['status']));

        this.loginForm = new FormGroup({
            login: new FormControl('', [Validators.min(6), Validators.max(72), Validators.required]),
            password: new FormControl('', [Validators.min(6), Validators.max(72), Validators.required]),
        });

        this.registerForm = new FormGroup({

        });
    }

    ngOnInit(): void {

    }

    private checkType(type: string): string {
        const loginType = AccountType.LOGIN
            .toString()
            .toLowerCase();

        const registerType = AccountType.REGISTER
            .toString()
            .toLowerCase();

        return type === loginType
            ? loginType
            : registerType;
    }

    public loginType(): boolean {
        return this.type === AccountType.LOGIN;
    }

    public submit(loginType: boolean): void {
        // Add not valid toastr's
        loginType ?
            this.toastr.invokeInfo("You have been logged successfully", "Logging", Position.BOTTOM_RIGHT)
            : this.toastr.invokeInfo("You have been signed up successfully", "Sign up", Position.BOTTOM_RIGHT)
    }


    public get getLoginForm(): FormGroup {
        return this.loginForm;
    }

    public get getRegisterForm(): FormGroup {
        return this.registerForm;
    }

}
