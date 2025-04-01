import { Routes } from '@angular/router';
import { FormulariopeliculasComponent } from './formulariopeliculas/formulariopeliculas.component';
import { VistaprincipalComponent } from './vistaprincipal/vistaprincipal.component';
import { ListacoleccionComponent } from './listacoleccion/listacoleccion.component';
import { FormularioLibrosComponent } from './formulariolibros/formulariolibros.component';
import { ListalibrosComponent } from './listalibros/listalibros.component';
import { ListapeliculasComponent } from './listapeliculas/listapeliculas.component';
import { FormulariomusicaComponent } from './formulariomusica/formulariomusica.component';
import { ListamusicaComponent } from './listamusica/listamusica.component';
import { ListapersonasComponent } from './listapersonas/listapersonas.component';
import { FormulariopersonasComponent } from './formulariopersonas/formulariopersonas.component';
import { DetalleUsuarioComponent } from './detalleusuario/detalleusuario.component'; 
import { DetallePeliculaComponent } from './detalle-pelicula/detalle-pelicula.component'
import { DetalleMusicaComponent } from './detalle-musica/detalle-musica.component'
import { DetalleLibroComponent } from './detalle-libro/detalle-libro.component'


export const routes: Routes = [
    { path: '', component: VistaprincipalComponent },
    { path: 'formulariopeliculas', component: FormulariopeliculasComponent },
    { path: 'listacoleccion', component: ListacoleccionComponent },
    { path: 'formulariolibros', component: FormularioLibrosComponent },
    { path: 'listalibros', component: ListalibrosComponent },
    { path: 'listapeliculas', component: ListapeliculasComponent },
    { path: 'formulariomusica', component: FormulariomusicaComponent },
    { path: 'listamusica', component: ListamusicaComponent },
    { path: 'listausuarios', component: ListapersonasComponent },
    { path: 'vistaprincipal', component: VistaprincipalComponent },
    { path: 'formulariopersonas', component: FormulariopersonasComponent },
    { path: 'detalleusuario/:id', component: DetalleUsuarioComponent},
    { path: 'detallepelicula/:id', component: DetallePeliculaComponent },
    { path: 'detallemusica/:id', component: DetalleMusicaComponent },
    { path: 'detallelibro/:id', component: DetalleLibroComponent },
];
