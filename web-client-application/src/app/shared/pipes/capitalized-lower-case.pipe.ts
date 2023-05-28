import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'capitalizedLowerCase'
})
export class CapitalizedLowerCasePipe implements PipeTransform {

  transform(value: string): string {
    const transformedString: string = value.charAt(0).toUpperCase() + value.slice(1).toLowerCase()
    return transformedString.replaceAll('_', ' ');
  }

}

``
