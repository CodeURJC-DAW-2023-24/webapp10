import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { NgModule } from '@angular/core';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ContactComponent } from './contact/contact.component';
import { AboutusComponent } from './aboutus/aboutus.component';
import { ResponsablegameComponent } from './responsablegame/responsablegame.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { BetsComponent } from './bets/bets.component';


export const routes: Routes = [
    { path: '', redirectTo: 'home', pathMatch: 'full'},
    { path: 'home', component: HomeComponent },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'contact', component: ContactComponent },
    { path: 'aboutus', component: AboutusComponent },
    { path: 'responsablegame', component: ResponsablegameComponent },
    { path: 'bets', component: BetsComponent},
    { path: '**', component: NotfoundComponent}

    
   
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }

