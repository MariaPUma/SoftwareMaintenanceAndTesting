//Realizar predicción test: sobre uno de los pacientes de un médico, y sobre una imagen, comprueba que se puede
// realizar una predicción con el algoritmo de IA en el formulario de mostrar información sobre una imagen.
// Para facilitar el desarrollo del test, sube la imagen (ej. healthty.png) de forma manual sobre un paciente
// antes de ejecutar el test.


import { browser } from 'k6/browser';
import { check } from 'https://jslib.k6.io/k6-utils/1.5.0/index.js';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        ui: {
            executor: 'shared-iterations',
            options: {
                browser: {
                    type: 'chromium',
                },
            },
        },
    },
    thresholds: {
        checks: ['rate==1.0'],
    },
};

export default async function () {
    const page = await browser.newPage();

    try {
        await page.goto('http://localhost:4200/');
        await page.locator('input[name="nombre"]').type('Paulino');
        await page.locator('input[name="DNI"]').type('54251265H');
        sleep(3);
        const submitButton = page.locator('button[name="login"]');

        await Promise.all([page.waitForNavigation(), submitButton.click()]);

        
        sleep(2);


    } finally {
        sleep(3);

        await page.close();
    }
}
