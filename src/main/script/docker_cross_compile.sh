#!/bin/bash
target_dir=$1
target_platform=$2
jar_file=$3
# linux arm64 = linux/aarch64
# linux x86 = linux/amd64
if [ "$target_platform" == "" ]; then
  echo "please input the target platform: linux/aarch64 or linux/amd64 ..."
  exit
fi
result_platform=$(echo "$target_platform" | sed 's/\//\-/g')
native_image_file_name="ce-${result_platform}"

echo "The build directory is: $target_dir"
echo "The target platform is: $target_platform"

build_native_command="native-image --no-fallback -H:+UnlockExperimentalVMOptions -jar ${jar_file} ${native_image_file_name}"
# shellcheck disable=SC2006
container_id=`docker run --platform "${target_platform}" -v "${target_dir}":/app -itd --rm --entrypoint /bin/bash ghcr.io/graalvm/native-image-community:21`
echo "The container id is: ${container_id}"

echo "build command: ${build_native_command}"
docker exec "${container_id}" bash -c "${build_native_command}"
docker stop "${container_id}"