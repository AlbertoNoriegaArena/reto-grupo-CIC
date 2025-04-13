import { Routes } from '@angular/router';
import { FormulariopeliculasComponent } from './components/formulariopeliculas/formulariopeliculas.component';
import { VistaprincipalComponent } from './components/vistaprincipal/vistaprincipal.component';
import { FormularioLibrosComponent } from './components/formulariolibros/formulariolibros.component';
import { ListalibrosComponent } from './components/listalibros/listalibros.component';
import { ListapeliculasComponent } from './components/listapeliculas/listapeliculas.component';
import { FormulariomusicaComponent } from './components/formulariomusica/formulariomusica.component';
import { ListamusicaComponent } from './components/listamusica/listamusica.component';
import { ListapersonasComponent } from './components/listapersonas/listapersonas.component';
import { FormulariopersonasComponent } from './components/formulariopersonas/formulariopersonas.component';
import { DetalleUsuarioComponent } from './components/detalleusuario/detalleusuario.component'; 
import { DetallePeliculaComponent } from './components/detalle-pelicula/detalle-pelicula.component'
import { DetalleMusicaComponent } from './components/detalle-musica/detalle-musica.component'
import { DetalleLibroComponent } from './components/detalle-libro/detalle-libro.component'
import { ListaprestamosComponent } from './components/listaprestamos/listaprestamos.component';
import { DetallePrestamoComponent } from './components/detalle-prestamo/detalle-prestamo.component'
import { ListaFormatoComponent } from './components/listaformatos/listaformatos.component';
import { ListaitemsComponent } from './components/listaitems/listaitems.component';



export const routes: Routes = [
    { path: '', component: VistaprincipalComponent, data: { breadcrumb: 'Inicio' } },
    { path: 'formulariopeliculas', component: FormulariopeliculasComponent, data: { breadcrumb: 'Agregar Película' } },
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
    { path: 'detalleprestamo/:id', component: DetallePrestamoComponent, data: { breadcrumb: 'Detalles Préstamo' } },
    { path: 'listaformatos', component: ListaFormatoComponent, data: { breadcrumb: 'Lista de Formatos' } },
    { path: 'listaitems', component: ListaitemsComponent, data: { breadcrumb: 'Lista de Artículos' } },
  ];
