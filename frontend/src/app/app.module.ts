import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { AppRoutingModule } from './app.routes';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { HeaderComponent } from "./header/header.component";
import { FooterComponent } from "./footer/footer.component";
import { CopyrightComponent } from './copyright/copyright.component';
import { LoginComponent } from './login/login.component';

@NgModule({
 declarations: [
    AppComponent, 
    HomeComponent,
    HeaderComponent, 
    FooterComponent,
    CopyrightComponent,
    LoginComponent,

],

 imports: [
    BrowserModule, 
    FormsModule,
    AppRoutingModule 
],

 bootstrap: [AppComponent]
})
export class AppModule {}