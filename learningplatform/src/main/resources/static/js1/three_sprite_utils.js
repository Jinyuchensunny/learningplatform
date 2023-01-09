var three_sprite_utils_vars = {
    spriteInfoStorage: [],
    spriteParams: null,
    scene_src: null,
}

function SetScene(_scene_src) {
    three_sprite_utils_vars.scene_src = _scene_src;
}

function SwitchSprite(str1, _str, _position, attachedObject) {
    var sprite_tmp;
    sprite_tmp = findSprite(attachedObject)
    if (sprite_tmp == null)
        return CreateSprite(str1, _str, _position, attachedObject);
    else {
        return RemoveSprite(attachedObject);
    }
}

function RemoveSprite(attachedObject) {
    var spriteIndex = findSpriteIndex(attachedObject);
    if (spriteIndex == -1)
        return null;
    three_sprite_utils_vars.scene_src.remove(three_sprite_utils_vars.spriteInfoStorage[spriteIndex]['spriteItem']);
    three_sprite_utils_vars.spriteInfoStorage.splice(spriteIndex, 1)
    return null;
}

function findSprite(attachedObject) {
    var spriteIndex = findSpriteIndex(attachedObject);
    if (spriteIndex == -1)
        return null;
    return three_sprite_utils_vars.spriteInfoStorage[spriteIndex]['spriteItem']

}

function findSpriteIndex(attachedObject) {
    var attachedObj_tmp;
    var flag = false;
    var i = -1;
    for (i = 0; i < three_sprite_utils_vars.spriteInfoStorage.length; i++) {
        var spriteInfo_tmp = three_sprite_utils_vars.spriteInfoStorage[i];
        console.log(spriteInfo_tmp);
        attachedObj_tmp = spriteInfo_tmp['objItem'];
        if (attachedObj_tmp == attachedObject) {
            flag = true;
            break;
            return spriteInfo_tmp['spriteItem'];
        }
    }
    if (i == three_sprite_utils_vars.spriteInfoStorage.length) {
        return -1;
    }
    return i;
}

function CreateSprite(str1, str2, _position, _obj) {
    var newSprite = makeTextSprite(str1, str2, {position: _position});
    var spriteInfoItem = {'objItem': _obj, 'spriteItem': newSprite};
    three_sprite_utils_vars.spriteInfoStorage.push(spriteInfoItem);
    return newSprite;
}


function makeTextSprite(name, message, parameters) {

    if (parameters === undefined) parameters = {};

    var fontface = parameters.hasOwnProperty("fontface") ?
        parameters["fontface"] : "Cjl";

    // 字体大小
    var fontsize = 12;

    // 边框厚度
    var borderThickness = parameters.hasOwnProperty("borderThickness") ?
        parameters["borderThickness"] : 2;

    // 边框颜色
    var borderColor = parameters.hasOwnProperty("borderColor") ?
        parameters["borderColor"] : {r: 90, g: 90, b: 90, a: 1.0};

    var position = parameters.hasOwnProperty("position") ?
        {x: 450, y: 100, z: 0} : parameters["position"];

    // 创建画布
    var canvas = document.createElement('canvas');
    var context = canvas.getContext('2d');

    // 字体加粗
    context.font = "Bold " + fontsize + "px " + fontface;

    //背景颜色
    context.fillStyle = "rgba(255,165,0,0.8)";

    //边框的颜色
    context.strokeStyle = "rgba(" + borderColor.r + "," + borderColor.g + ","
        + borderColor.b + "," + borderColor.a + ")";
    context.lineWidth = borderThickness;

    // 绘制圆角矩形
    roundRect(context, borderThickness / 2, borderThickness / 2, 150 + borderThickness, 150 + borderThickness, 6);

    // 字体颜色
    context.fillStyle = "rgba(255, 255, 255, 1)";
    context.fillText("工作室名称:", borderThickness, fontsize + borderThickness);
    context.fillText(name, borderThickness + 2 * fontsize, 2 * fontsize + borderThickness);
    context.fillText("简介：", borderThickness, 3 * fontsize + borderThickness);

    for (let i = 0; i < 9; i++) {
        let space = 12
        let word = message.substr(i * 10, space)
        context.fillText(word, borderThickness, (4 + i) * fontsize + borderThickness);

    }
    // 画布内容用于纹理贴图
    var texture = new THREE.Texture(canvas);
    texture.needsUpdate = true;

    var spriteMaterial = new THREE.SpriteMaterial({map: texture});
    var sprite = new THREE.Sprite(spriteMaterial);

    console.log(sprite.spriteMaterial);

    // 缩放比例
    sprite.position.set(position.x, position.y, position.z);
    sprite.scale.set(500, 300, 1);

    return sprite;

}

function roundRect(ctx, x, y, w, h, r) {

    ctx.beginPath();
    ctx.moveTo(x + r, y);
    ctx.lineTo(x + w - r, y);
    ctx.quadraticCurveTo(x + w, y, x + w, y + r);
    ctx.lineTo(x + w, y + h - r);
    ctx.quadraticCurveTo(x + w, y + h, x + w - r, y + h);
    ctx.lineTo(x + r, y + h);
    ctx.quadraticCurveTo(x, y + h, x, y + h - r);
    ctx.lineTo(x, y + r);
    ctx.quadraticCurveTo(x, y, x + r, y);
    ctx.closePath();
    ctx.fill();
    ctx.stroke();

}
