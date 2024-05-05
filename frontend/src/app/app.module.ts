import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';
import { NgxChartsModule } from '@swimlane/ngx-charts';

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
import { BetsComponent } from './bets/bets.component';
import { SinglebetComponent } from './singlebet/singlebet.component';
import { WalletComponent } from './wallet/wallet.component';
import { BetsadminComponent } from './betsadmin/betsadmin.component';
import { ProfileComponent } from './profile/profile.component';
import { EditEventComponent } from './edit-event/edit-event.component';
import { CartComponent } from './cart/cart.component';
import { CommentComponent } from './comment/comment.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';

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
    HeaderNoTransparentComponent,
    BetsComponent,
    SinglebetComponent,
    WalletComponent,
    BetsadminComponent,
    ProfileComponent,
    EditEventComponent,
    CartComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule,
    MatIconModule,
    NgxChartsModule
  ],
  bootstrap: [AppComponent],
  providers: [
    provideAnimationsAsync()
  ]
})
export class AppModule {}
