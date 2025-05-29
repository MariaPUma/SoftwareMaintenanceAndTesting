//Crear paciente test: una vez realizado el login, se mostrará el listado de pacientes de un médico.
// Sobre este listado se creará un nuevo paciente. Comprueba que una vez creado el paciente este forma parte
// de la lista de pacientes del médico.

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

        const addButton = page.locator('button[name="add"]')
        await Promise.all([page.waitForNavigation(), addButton.click()]);

        await page.locator('input[name="dni"]').type('321');
        await page.locator('input[name="nombre"]').type('Rubena');
        await page.locator('input[name="edad"]').type('3');
        await page.locator('input[name="cita"]').type('Pediatria');

        const createButton = page.locator('button[type="submit"]')
        sleep(2);

        await Promise.all([page.waitForNavigation(), createButton.click()]);

        sleep(2);

        // let len = await page.locator("table tbody tr").count();
        // console.log(len);
        // await check(page.locator('table'), {
        //     'patient created successfully': async (lo) => (await (lo.$$("tbody tr")[len-1].$('td[name="nombre"]').textContent())) === 'Rubena'
        // });

        // const rows = page.locator('table tbody tr');
        // const len = await rows.count();
        // console.log(len);

        await page.waitForSelector('table tbody tr', { timeout: 10000 }); // Espera hasta 10 segundos

        const rowsLocator = page.locator('table tbody tr');
        // SOLUCIÓN: Usar .all() y .length en lugar de .count()
        const rowElements = await rowsLocator.all(); // Devuelve un array de ElementHandle
        const len = rowElements.length;             // Obtiene la cantidad de elementos
        console.log(`Número de filas encontradas: ${len}`);

        if (len === 0) {
            console.error("No se encontraron filas en la tabla. La verificación del paciente fallará.");
            // Forzar un fallo en la comprobación si no hay filas
            check(null, {
                'patient created successfully': () => false,
            });
        } else {
            await check(page.locator('table'), { // El contexto del check es el elemento 'table'
                'patient created successfully': async (tableElement) => {
                    // tableElement es el Locator de la tabla
                    const lastRow = tableElement.locator('tbody tr').nth(len - 1);
                    const nameCell = lastRow.locator('td[name="nombre"]');

                    try {
                        // Esperar a que la celda específica esté visible antes de obtener su texto
                        await nameCell.waitFor({state: 'visible', timeout: 5000}); // Espera hasta 5s
                    } catch (e) {
                        console.error(`La celda del nombre en la última fila no está visible o no se encontró: ${e.message}`);
                        return false; // La celda no está, así que la comprobación falla
                    }

                    const text = await nameCell.textContent();
                    console.log(`Texto en la celda de nombre de la última fila: "${text}"`);
                    return text === 'Rubena';
                }
            });
        }




    } finally {
        sleep(3);

        await page.close();
    }
}
