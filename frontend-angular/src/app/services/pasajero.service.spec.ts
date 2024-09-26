import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { PasajeroService } from './pasajero.service';

describe('PasajeroService', () => {
  let service: PasajeroService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [ PasajeroService ]
    });
    service = TestBed.inject(PasajeroService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
