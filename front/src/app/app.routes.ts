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
import { ListaprestamosComponent } from './listaprestamos/listaprestamos.component';
import { DetallePrestamoComponent } from './detalle-prestamo/detalle-prestamo.component'



export const routes: Routes = [
    { path: '', component: VistaprincipalComponent, data: { breadcrumb: 'Inicio' } },
    { path: 'formulariopeliculas', component: FormulariopeliculasComponent, data: { breadcrumb: 'Agregar Película' } },
    { path: 'listacoleccion', component: ListacoleccionComponent, data: { breadcrumb: 'Colección' } },
    { path: 'formulariolibros', component: FormularioLibrosComponent, data: { breadcrumb: 'Agregar Libro' } },
    { path: 'listalibros', component: ListalibrosComponent, data: { breadcrumb: 'Lista de Libros' } },
    { path: 'listapeliculas', component: ListapeliculasComponent, data: { breadcrumb: 'Lista de Películas' } },
    { path: 'formulariomusica', component: FormulariomusicaComponent, data: { breadcrumb: 'Agregar Música' } },
    { path: 'listamusica', component: ListamusicaComponent, data: { breadcrumb: 'Lista de Música' } },
    { path: 'listausuarios', component: ListapersonasComponent, data: { breadcrumb: 'Lista de Usuarios' } },
    { path: 'vistaprincipal', component: VistaprincipalComponent, data: { breadcrumb: 'Inicio' } },
    { path: 'formulariopersonas', component: FormulariopersonasComponent, data: { breadcrumb: 'Agregar Persona' } },
    { path: 'detalleusuario/:id', component: DetalleUsuarioComponent, data: { breadcrumb: 'Detalles Usuario' } },
    { path: 'detallepelicula/:id', component: DetallePeliculaComponent, data: { breadcrumb: 'Detalles Película' } },
    { path: 'detallemusica/:id', component: DetalleMusicaComponent, data: { breadcrumb: 'Detalles Música' } },
    { path: 'detallelibro/:id', component: DetalleLibroComponent, data: { breadcrumb: 'Detalles Libro' } },
    { path: 'listaprestamos', component: ListaprestamosComponent, data: { breadcrumb: 'Lista de Préstamos' } },
    { path: 'detalleprestamo/:id', component: DetallePrestamoComponent, data: { breadcrumb: 'Detalles Préstamo' } }
  ];
