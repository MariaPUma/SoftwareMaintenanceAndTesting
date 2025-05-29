import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
    vus: 5, // Number of virtual users
    duration: '1m', // Duration of the test
    http_req_duration: [{
        threshold: 'avg<=100'
    }],// average duration requests should be under 100ms
    http_req_failed: [{
        threshold: 'rate==0',
        abortOnFail: true,
    }]// rate of failed requests should be 0
}

export default  () => {
    const urlRes = http.get('http://localhost:8080/medico/1');
    sleep(1);
}