import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AccountComponent} from './src/app/components/account/account.component';
import {MainComponent} from './src/app/components/main/main.component';

const routes: Routes = [
    {
        path: "",
        component: MainComponent
    },

    {
        path: "account",
        component: AccountComponent
    }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
