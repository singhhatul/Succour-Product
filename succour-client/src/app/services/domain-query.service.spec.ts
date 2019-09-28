import { TestBed } from '@angular/core/testing';

import { DomainQueryService } from './domain-query.service';

describe('DomainQueryService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DomainQueryService = TestBed.get(DomainQueryService);
    expect(service).toBeTruthy();
  });
});
