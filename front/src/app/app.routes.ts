import { Routes } from '@angular/router';
import { ListacoleccionComponent } from '../listacoleccion/listacoleccion.component';
import { FormulariocoleccionComponent } from '../formulariocoleccion/formulariocoleccion.component';
import { DetallecoleccionComponent } from '../detallecoleccion/detallecoleccion.component';



export const routes: Routes = [

{path:"listacoleccion",component:ListacoleccionComponent},
{path:"formulariocoleccion",component:FormulariocoleccionComponent},
{path:"detallecoleccion/:tipo",component:DetallecoleccionComponent},

];
