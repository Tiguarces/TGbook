import {CommonModule} from '@angular/common';
import {HttpClientModule} from '@angular/common/http';
import {NgModule} from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms';
import {BrowserModule} from '@angular/platform-browser';

import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {ToastrModule} from 'ngx-toastr';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AccountComponent} from './src/app/components/account/account.component';
import {FooterComponent} from './src/app/components/footer/footer.component';
import {MainComponent} from './src/app/components/main/main.component';
import {NavbarComponent} from './src/app/components/navbar/navbar.component';
import {BookHelper} from './src/app/Utils/BookHelper';
import {IconHelper} from './src/app/Utils/IconHelper';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ToastrHelper} from './src/app/Utils/ToastrHelper';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    MainComponent,
    FooterComponent,
    AccountComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FontAwesomeModule,
    CommonModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [ BookHelper, IconHelper, ToastrHelper ],
  bootstrap: [AppComponent]
})
export class AppModule { }
