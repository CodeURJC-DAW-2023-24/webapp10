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
import { RegisterComponent } from './register/register.component';
import { ContactComponent } from './contact/contact.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { ResponsablegameComponent } from './responsablegame/responsablegame.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { HeaderNoTransparentComponent } from './header-no-transparent/header-no-transparent.component';

@NgModule({
 declarations: [
    AppComponent, 
    HomeComponent,
    HeaderComponent, 
    FooterComponent,
    CopyrightComponent,
    LoginComponent,
    RegisterComponent,
    ContactComponent,
    AboutusComponent,
    ResponsablegameComponent,
    NotfoundComponent,
    HeaderNoTransparentComponent

],

 imports: [
    BrowserModule, 
    FormsModule,
    AppRoutingModule 
],

 bootstrap: [AppComponent]
})
export class AppModule {}