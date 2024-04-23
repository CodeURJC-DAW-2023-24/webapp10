import { RouterModule, Routes } from '@angular/router';
import { HeaderComponent } from './header/header.component'
import { FooterComponent } from './footer/footer.component';
import { NgModule } from '@angular/core';

export const routes: Routes = [
    { path: 'header', component: HeaderComponent },
    { path : 'footer', component: FooterComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutingModule { }

