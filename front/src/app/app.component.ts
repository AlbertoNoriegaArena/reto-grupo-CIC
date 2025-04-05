import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterOutlet, NavigationEnd, RouterModule } from '@angular/router';
import { filter, map, switchMap } from 'rxjs/operators';
import { CommonModule } from '@angular/common';
import { PrestamoService } from '../prestamo.service';
import { PeliculaService } from '../pelicula.service';
import { MusicaService } from '../musica.service';
import { LibroService } from '../libro.service';
import { PersonaService } from '../persona.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, RouterModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent implements OnInit {
  title = 'ColeccionPersonal';
  breadcrumbs: Array<{ label: string, url: string }> = [];

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private prestamoService: PrestamoService,
    private peliculaService: PeliculaService,
    private musicaService: MusicaService,
    private libroService: LibroService,
    private personaService: PersonaService
  ) { }

  ngOnInit() {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.activatedRoute.root),
      map(route => {
        while (route.firstChild) {
          route = route.firstChild;
        }
        return route;
      }),
      filter(route => route.outlet === 'primary'),
      switchMap(route => route.data)
    ).subscribe(data => {
      this.breadcrumbs = this.createBreadcrumbs(this.activatedRoute.root);
    });
  }

  createBreadcrumbs(route: ActivatedRoute, url: string = '', breadcrumbs: Array<{ label: string, url: string }> = []): Array<{ label: string, url: string }> {
    const children: ActivatedRoute[] = route.children;

    if (children.length === 0) {
      return breadcrumbs;
    }

    for (const child of children) {
      const routeURL: string = child.snapshot.url.map(segment => segment.path).join('/');
      const routeData = child.snapshot.data;

      if (routeURL) {
        url += `/${routeURL}`;

        if (breadcrumbs.length === 0) {
          breadcrumbs.push({ label: 'Inicio', url: '/' });
        }

        if (routeURL === 'listaprestamos') {
          breadcrumbs.push({ label: 'Préstamos', url: url });
        } else if (routeURL.startsWith('detalleprestamo')) {
          const prestamoId = child.snapshot.params['id'];
          this.prestamoService.getPrestamoById(prestamoId).subscribe(prestamo => {
            const itemName = prestamo.item.nombre;
            breadcrumbs.push({ label: 'Préstamos', url: '/listaprestamos' });
            breadcrumbs.push({ label: `Préstamos de: ${itemName}`, url: url });
          });
        } else if (routeURL === 'listapeliculas') {
          breadcrumbs.push({ label: 'Películas', url: url });
        } else if (routeURL.startsWith('detallepelicula')) {
          const peliculaId = child.snapshot.params['id'];
          this.peliculaService.buscarUno(peliculaId).subscribe(pelicula => {
            const itemName = pelicula.item.nombre;
            breadcrumbs.push({ label: 'Películas', url: '/listapeliculas' });
            breadcrumbs.push({ label: `${itemName}`, url: url });
          });
        } else if (routeURL === 'listamusica') {
          breadcrumbs.push({ label: 'Música', url: url });
        } else if (routeURL.startsWith('detallemusica')) {
          const musicaId = child.snapshot.params['id'];
          this.musicaService.buscarUno(musicaId).subscribe(musica => {
            const itemName = musica.item.nombre;
            breadcrumbs.push({ label: 'Música', url: '/listamusica' });
            breadcrumbs.push({ label: `${itemName}`, url: url });
          });
        } else if (routeURL === 'listalibros') {
          breadcrumbs.push({ label: 'Libros', url: url });
        } else if (routeURL.startsWith('detallelibro')) {
          const libroId = child.snapshot.params['id'];
          this.libroService.buscarUno(libroId).subscribe(libro => {
            const itemName = libro.item.nombre;
            breadcrumbs.push({ label: 'Libros', url: '/listalibros' });
            breadcrumbs.push({ label: `${itemName}`, url: url });
          });
        } else if (routeURL === 'listausuarios') {
          breadcrumbs.push({ label: 'Usuarios', url: url });
        } else if (routeURL.startsWith('detalleusuario')) {
          const usuarioId = child.snapshot.params['id'];
          this.personaService.getPersonaById(usuarioId).subscribe(usuario => {
            const itemName = usuario.nombre;
            breadcrumbs.push({ label: 'Usuarios', url: '/listausuarios' });
            breadcrumbs.push({ label: `${itemName}`, url: url });
          });
        } else if (routeData['breadcrumb']) {
          breadcrumbs.push({ label: routeData['breadcrumb'], url: url });
        }
      }

      return this.createBreadcrumbs(child, url, breadcrumbs);
    }

    return breadcrumbs;
  }
}
