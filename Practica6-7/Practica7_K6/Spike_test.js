import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    stages: [
        {duration: '2m', target: 216},
        {duration: '2m', target: 0}
    ],

    thresholds: {
        'http_req_failed': [
            { threshold: 'rate<=0.05', abortOnFail: true },
        ],
    },
};

export default  () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    sleep(1);
}