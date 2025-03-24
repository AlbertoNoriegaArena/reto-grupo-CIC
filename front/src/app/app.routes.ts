// src/app/app.routes.ts
import { Routes } from '@angular/router';
import { FormulariopeliculasComponent } from './formulariopeliculas/formulariopeliculas.component';
import { VistaprincipalComponent } from './vistaprincipal/vistaprincipal.component';
import { ListacoleccionComponent } from './listacoleccion/listacoleccion.component';
import { FormulariolibrosComponent } from './formulariolibros/formulariolibros.component';
import { ListalibrosComponent } from './listalibros/listalibros.component';
import { ListapeliculasComponent } from './listapeliculas/listapeliculas.component';
import { FormulariomusicaComponent } from './formulariomusica/formulariomusica.component';
import { ListamusicaComponent } from './listamusica/listamusica.component';

export const routes: Routes = [
    { path: '', component: VistaprincipalComponent },
    { path: 'formulariopeliculas', component: FormulariopeliculasComponent },
    { path: 'listacoleccion', component: ListacoleccionComponent },
    { path: 'formulariolibros', component: FormulariolibrosComponent },
    { path: 'listalibros', component: ListalibrosComponent },
    { path: 'listapeliculas', component: ListapeliculasComponent },
    { path: 'formulariomusica', component: FormulariomusicaComponent },
    { path: 'listamusica', component: ListamusicaComponent },
];
