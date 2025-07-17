export const CLOUDINARY_CLOUD_NAME = 'dfefl6j9b'; // Tvoj stvarni cloud name

export const getCloudinaryUrl = (imageId: string, options?: {
  width?: number;
  height?: number;
  crop?: string;
  quality?: number;
}) => {
  const baseUrl = `https://res.cloudinary.com/${CLOUDINARY_CLOUD_NAME}/image/upload`;
  const transformations = [];
  
  if (options?.width) transformations.push(`w_${options.width}`);
  if (options?.height) transformations.push(`h_${options.height}`);
  if (options?.crop) transformations.push(`c_${options.crop}`);
  if (options?.quality) transformations.push(`q_${options.quality}`);
  
  const transformString = transformations.length > 0 ? `/${transformations.join(',')}` : '';
  
  return `${baseUrl}${transformString}/${imageId}`;
};

// Za ikonice aktivnosti
export const getActivityIconUrl = (iconName: string) => {
  return getCloudinaryUrl(`hikebook/activities/${iconName}`, {
    width: 48,
    height: 48,
    crop: 'fill',
    quality: 90
  });
};

// Za slike staza
export const getTrailImageUrl = (imageId: string, options?: {
  width?: number;
  height?: number;
}) => {
  return getCloudinaryUrl(`hikebook/trails/${imageId}`, {
    width: options?.width || 400,
    height: options?.height || 300,
    crop: 'fill',
    quality: 85
  });
};

// Za placeholder slike
export const getPlaceholderImage = (width: number = 400, height: number = 300) => {
  return `https://placehold.co/${width}x${height}/22c55e/ffffff?text=Hike%26Book`;
};

// Za thumbnail slike
export const getThumbnailUrl = (imageId: string) => {
  return getCloudinaryUrl(`hikebook/trails/${imageId}`, {
    width: 200,
    height: 150,
    crop: 'fill',
    quality: 80
  });
};

// Za hero slike
export const getHeroImageUrl = (imageId: string) => {
  return getCloudinaryUrl(`hikebook/trails/${imageId}`, {
    width: 800,
    height: 400,
    crop: 'fill',
    quality: 85
  });
}; 