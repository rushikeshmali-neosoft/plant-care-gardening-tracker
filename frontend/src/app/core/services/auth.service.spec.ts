import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { AuthService } from './auth.service';
import { environment } from '../../../environments/environment';
import { AuthResponse, LoginRequest } from '../models/auth.model';

describe('AuthService', () => {
  let service: AuthService;
  let httpMock: HttpTestingController;

  const mockAuthResponse: AuthResponse = {
    accessToken: 'access-token',
    refreshToken: 'refresh-token',
    user: {
      id: 1,
      email: 'test@example.com',
      name: 'Test User',
      role: 'ROLE_USER',
      experienceLevel: 'BEGINNER',
      preferences: ''
    }
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthService]
    });
    service = TestBed.inject(AuthService);
    httpMock = TestBed.inject(HttpTestingController);
    localStorage.clear();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should login and store token', () => {
    const loginRequest: LoginRequest = { email: 'test@example.com', password: 'password' };

    service.login(loginRequest).subscribe(response => {
      expect(response).toEqual(mockAuthResponse);
      expect(localStorage.getItem('access_token')).toBe(mockAuthResponse.accessToken);
    });

    const req = httpMock.expectOne(`${environment.apiUrl}/auth/login`);
    expect(req.request.method).toBe('POST');
    req.flush(mockAuthResponse);
  });

  it('should logout and clear tokens', () => {
    localStorage.setItem('access_token', 'some-token');
    service.logout();
    expect(localStorage.getItem('access_token')).toBeNull();
  });
});
