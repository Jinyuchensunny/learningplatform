/**
 * Created by lenovo on 2017/3/21.
 */
function share(){
    var renderer=new THREE.WebGLRenderer();
    renderer.setClearColor(new THREE.Color(0xEEEEEE,1.0));
    renderer.setSize(window.innerWidth,window.innerHeight);
    renderer.shadowMapEnabled=true;

    var planeGeometry=new THREE.PlaneGeometry(60,30);
    var planeMaterial=new THREE.MeshLambertMaterial({color:0xffffff});
    var plane=new THREE.Mesh(planeGeometry,planeMaterial);
    plane.rotation.x=-0.5*Math.PI;
    plane.position.set(15,0,0);

    var scene=new THREE.Scene();
    scene.add(plane);

    return{
        scene:scene,
        camera:new THREE.PerspectiveCamera(60,window.innerWidth/window.innerHeight,0.1,1000),
        renderer:renderer
    }
}
