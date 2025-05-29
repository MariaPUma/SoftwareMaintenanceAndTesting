// Login test: prueba end-to-end que introduzca los datos de un médico previamente 
// creado en la base de datos H2 y se lo loguea correctamente en el sistema.

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



    // let len = ((await page.$$('table tbody tr')).length);

    await check(page.locator('h2'), {
      'login success': async (lo) => (await lo.textContent()) === 'Listado de pacientes',
    });

    // await check(page.locator('div[class="alert alert-success"]'), {
    //   'alerta correcta': async (lo) => (await lo.textContent()) === 'La cuenta ha sido creada correctamente.',
    // });

    // sleep(3);

    // const removeButton = page.locator('input[name="eliminar"]');

    // await Promise.all([page.waitForNavigation(), removeButton.click()]);

    // const rows = await page.locator('table tbody tr').all();

    // await check(rows, {
    //     'La tabla no tiene filas':async (r) => (await r.length) === 0,
    // });

    // await check(page.locator('div[class="alert alert-success"]'), {
    //   'alerta correcta': async (lo) => (await lo.textContent()) === 'La cuenta ha sido eliminada correctamente.',
    // });

  } finally {
    sleep(3);

    await page.close();
  }
}

// Open a cmd prompt and run the following command to execute the script:
// set "K6_BROWSER_HEADLESS=false" && k6 run browser.js