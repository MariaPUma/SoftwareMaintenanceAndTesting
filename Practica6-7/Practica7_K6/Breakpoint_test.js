import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    scenarios: {
        breakpoint: {
            executor: 'ramping-arrival-rate',
            preAllocatedVUs: 1000,
            maxVUs: 100000000,
            stages: [
                {duration: '10m', target: 100000},
            ],
        },
    },

    thresholds: {
        'http_req_failed': [
            { threshold: 'rate<=0.01', abortOnFail: true },
        ],
    },
}

// Parte 2 del test (sin executor)

// export const options = {
//     stages: [
//         {duration: '10m', target: 100000},
//     ],

//     thresholds: {
//         'http_req_failed': [
//             { threshold: 'rate<=0.01', abortOnFail: true },
//         ],
//     },
// }

export default  () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    sleep(1);
}

